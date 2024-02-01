package com.example.contacts.display;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.contacts.MainActivity;
import com.example.contacts.MainActivityData;
import com.example.contacts.R;
import com.example.contacts.database.Contact;
import com.example.contacts.database.ContactDAO;
import com.example.contacts.database.DBInstance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactList extends Fragment {

    private Contact imContact = new Contact();;
    private Adapter adapter;
    private RecyclerView rv;

    private MainActivityData mAD;
    private int contactId;
    ContactDAO contactDAO;
    private static final int REQUEST_READ_CONTACT_PERMISSION = 3;
    ActivityResultLauncher<Intent> selectContactResultLauncher;

    public ContactList() {
    }
    public static ContactList newInstance(String param1, String param2) {
        ContactList fragment = new ContactList();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectContactResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        processResult(data);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pView = inflater.inflate(R.layout.fragment_contact_list, container, false);

        mAD = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        contactDAO = DBInstance.getDatabase(getActivity().getApplicationContext()).contactDAO();

        rv = pView.findViewById(R.id.recyclerView);
        Button add = pView.findViewById(R.id.button);
        Button importContact = pView.findViewById(R.id.button3);

        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        adapter = new Adapter(contactDAO, mAD);
        rv.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAD.setFragValue(1);
            }
        });

        importContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.READ_CONTACTS},
                            REQUEST_READ_CONTACT_PERMISSION);
                } else {
                    createImportIntent();
                }
            }});

        return pView;
    }

    private void createImportIntent()
    {
        Intent intent = new Intent();                           //creating an implicit intent
        intent.setAction(Intent.ACTION_PICK);                   //setting action
        intent.setData(ContactsContract.Contacts.CONTENT_URI);  //setting data

        selectContactResultLauncher.launch(intent); //launch with result launcher to get result
    }

    private void processResult(Intent data)
    {
        Uri contactUri = data.getData();
        String[] queryFields = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        Cursor c = getActivity().getContentResolver().query(
                contactUri, queryFields, null, null, null);

        try {
            if (c.getCount() > 0 && c != null && c.moveToFirst()) {
                this.contactId = c.getInt(0);         // ID first
                String contactName = c.getString(1); // Name second
                imContact.name = contactName;

                //IF Name is unique get email ID and phone no
                if (contactDAO.getContactByNum(contactName) == null)
                {
                    //get email and phone no
                        getPhoneNumber();
                        getEmailAdd();

                    System.out.println("Initial size" + contactDAO.getAllContacts().size());
                    contactDAO.insert(imContact);
                    System.out.println("\n\n\n\nAfter inserting" + contactDAO.getAllContacts().size());
                    adapter = new Adapter(contactDAO, mAD);
                    rv.setAdapter(adapter);
                }
                else
                {
                    Toast toast = Toast.makeText(getActivity(), "Name already exists", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
        finally {
            c.close();
        }
    }

    private void getPhoneNumber(){
        String result= null;
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] queryFields = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String whereClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        String [] whereValues = new String[]{
                String.valueOf(this.contactId)
        };
        Cursor c = getActivity().getContentResolver().query(
                phoneUri, queryFields, whereClause,whereValues, null);
        try{
            if (c.getCount() > 0) {
                c.moveToFirst();
                String phoneNumber = c.getString(0);

                //checks here
                if(phoneNumber.length() == 10) {
                    result = phoneNumber;
                }
            }
        }
        finally {
            c.close();
        }

        imContact.phoneNo = result;
    }

    private void getEmailAdd()
    {
        String result = null;
        Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String[] queryFields = new String[] {
               ContactsContract.CommonDataKinds.Email.ADDRESS
        };

        String whereClause = ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?";
        String [] whereValues = new String[]{
                String.valueOf(this.contactId)
        };

        Cursor c = getActivity().getContentResolver().query(
                emailUri, queryFields, whereClause, whereValues, null);

        try{
            if (c.getCount() > 0) {
                c.moveToFirst();
                    String email = c.getString(0);

                    //checks here
                    if(validEmail(email))
                    {
                        result = email;
                    }
            }

        }
        finally {
            c.close();
        }
        imContact.email = result;
    }

    private boolean validEmail(String email)
    {
        //REGEX TAKEN FROM: https://www.baeldung.com/java-email-validation-regex
        String regex = "^(?i)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
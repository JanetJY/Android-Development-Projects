package com.example.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.contacts.database.Contact;
import com.example.contacts.database.ContactDAO;
import static android.app.Activity.RESULT_OK;
import com.example.contacts.database.DBInstance;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProfile extends Fragment {
    private ActivityResultLauncher<Intent> thumNailLauncher;
    private Bitmap picB = null;
    ImageView pic;

    ContactDAO contactDAO;
    public AddProfile() {
        // Required empty public constructor
    }

    public static AddProfile newInstance(String param1, String param2) {
        AddProfile fragment = new AddProfile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       thumNailLauncher = registerForActivityResult(
                       new ActivityResultContracts.StartActivityForResult(),
                       result -> {
                           if (result.getResultCode() == RESULT_OK) {
                               Intent data = result.getData();
                               Bitmap image = (Bitmap) data.getExtras().get("data");
                               if (image != null) {
                                   picB = image;
                                   pic.setImageBitmap(picB);

                               }

                           }
                       });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pView = inflater.inflate(R.layout.fragment_add_profile, container, false);
        contactDAO = DBInstance.getDatabase(getActivity().getApplicationContext()).contactDAO();
        MainActivityData mAD = new ViewModelProvider(getActivity()).get(MainActivityData.class);


        ImageView arrow = pView.findViewById(R.id.arrow);
        ImageButton x = pView.findViewById(R.id.delete);
        x.setVisibility(View.INVISIBLE);
        pic = pView.findViewById(R.id.imageView);
        Button create = pView.findViewById(R.id.button2);
        EditText name = pView.findViewById(R.id.editTextText);
        EditText email = pView.findViewById(R.id.editTextEmailAddress);
        EditText phone = pView.findViewById(R.id.editTextPhone);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAD.setFragValue(0);
                if(mAD.getToUpdate() != null)
                {
                    mAD.setToUpdate(null);
                }
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                thumNailLauncher.launch(intent);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sName = name.getText().toString();
                String sEmail = email.getText().toString();
                String sPhone = phone.getText().toString();
                Contact contact = validContact(sName, sEmail, sPhone, picB);
                picB = null;

                //Validate all fields here
                //create new

                if(!sName.isEmpty()) {
                    if (mAD.getToUpdate() == null) {
                        if (contactDAO.getContactByNum(sName) == null)
                        {
                            contactDAO.insert(contact);
                            mAD.setFragValue(0);
                        }
                        else
                        {
                            Toast toast = Toast.makeText(getActivity(), "Name already exists", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {

                        Contact oldVersion = mAD.getToUpdate();
                        mAD.setToUpdate(null);

                        if (sName.equals(oldVersion.name))
                        {
                            contactDAO.update(contact);
                        }
                        else
                        {
                            if (contactDAO.getContactByNum(sName) == null) {
                                contactDAO.delete(oldVersion);
                                contactDAO.insert(contact);
                            }
                            else
                                {
                                    Toast toast = Toast.makeText(getActivity(), "Name already exists", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                        }
                        mAD.setFragValue(0);
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(getActivity(),
                            "Name cannot be empty!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        return pView;
    }

    public void onResume() {
        super.onResume();
        View view = getView();
        MainActivityData mAD = new ViewModelProvider(getActivity()).get(MainActivityData.class);

        EditText name = view.findViewById(R.id.editTextText);
        EditText email = view.findViewById(R.id.editTextEmailAddress);
        EditText phone = view.findViewById(R.id.editTextPhone);
        ImageButton x = view.findViewById(R.id.delete);
        x.setVisibility(View.VISIBLE);

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAD.getToUpdate() != null)
                {
                    contactDAO.delete(mAD.getToUpdate());
                    mAD.setToUpdate(null);
                    mAD.setFragValue(0);
                }
            }
        });

        if (mAD.getToUpdate() == null) {
            name.setText("");
            email.setText("");
            phone.setText("");
        }
        else
        {
            Button create = view.findViewById(R.id.button2);
            create.setText("Update");

            Contact c = mAD.getToUpdate();
            name.setText(c.name);
            if (c.email != null)
            {
                email.setText(c.email);
            }
            else
            {
                email.setText("");
            }

            if(c.phoneNo != null)
            {
               phone.setText(String.valueOf(c.phoneNo));
            }
            else
            {
                phone.setText("");
            }

            if (c.imageData != null && picB == null)
            {
                Bitmap image = convertToBitmap(c.imageData);
                picB = image;
                pic.setImageBitmap(image);
            }
        }
    }

    private Contact validContact(String sName, String sEmail, String sPhone, Bitmap profile)
    {
        Contact contact = new Contact();
        contact.name = sName;
        contact.email = null;
        contact.phoneNo = null;
        contact.imageData = convertToByte(profile);

        if (validEmail(sEmail)) {
            contact.email = sEmail;
        }
        if (sPhone.length() == 10) {
            contact.phoneNo = sPhone;
        }

        return contact;
    }
    private boolean validEmail(String email)
    {
        //REGEX TAKEN FROM: https://www.baeldung.com/java-email-validation-regex
        String regex = "^(?i)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public byte[] convertToByte(Bitmap bitmap) {
        if(bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public Bitmap convertToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}

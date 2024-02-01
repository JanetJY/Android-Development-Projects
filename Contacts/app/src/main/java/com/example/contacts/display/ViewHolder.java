package com.example.contacts.display;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.MainActivityData;
import com.example.contacts.R;
import com.example.contacts.database.Contact;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView pic;
    private Contact contact;
    public ViewHolder(@NonNull View itemView, MainActivityData mAD) {
        super(itemView);

        pic = itemView.findViewById(R.id.pic);
        name = itemView.findViewById(R.id.name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAD.setFragValue(1);
                mAD.setToUpdate(contact);
            }
        });
    }

    public void bind(Contact c)
    {
        contact = c;
        name.setText(contact.name);
        if(c.imageData != null) {
            Bitmap image = BitmapFactory.decodeByteArray(c.imageData, 0, c.imageData.length);
            pic.setImageBitmap(image);
        }

    }
}

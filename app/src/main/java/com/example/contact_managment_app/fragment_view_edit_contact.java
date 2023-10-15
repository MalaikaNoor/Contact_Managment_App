package com.example.contact_managment_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Button;

public class fragment_view_edit_contact extends Fragment {

    private ImageView contactPhotoImageView;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button editButton;
    private Button deleteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_edit_contact, container, false);

        // Initialize UI elements
        contactPhotoImageView = rootView.findViewById(R.id.image_contact_photo);
        nameEditText = rootView.findViewById(R.id.edit_text_name);
        phoneEditText = rootView.findViewById(R.id.edit_text_phone);
        emailEditText = rootView.findViewById(R.id.edit_text_email);
        editButton = rootView.findViewById(R.id.button_edit);
        deleteButton = rootView.findViewById(R.id.button_delete);

        // Set up UI element actions and data population here

        return rootView;
    }
}
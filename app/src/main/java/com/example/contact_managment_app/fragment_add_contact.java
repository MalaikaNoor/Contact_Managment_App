package com.example.contact_managment_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.Toast;
import android.content.pm.PackageManager;

import androidx.fragment.app.Fragment;

public class fragment_add_contact extends Fragment {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private ImageView contactPhotoImageView;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button capturePhotoButton;
    private Button saveButton;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_contact, container, false);

        // Initialize UI elements
        contactPhotoImageView = rootView.findViewById(R.id.image_contact_photo);
        nameEditText = rootView.findViewById(R.id.edit_text_name);
        phoneEditText = rootView.findViewById(R.id.edit_text_phone);
        emailEditText = rootView.findViewById(R.id.edit_text_email);
        capturePhotoButton = rootView.findViewById(R.id.button_capture_photo);
        saveButton = rootView.findViewById(R.id.button_save);

        // Set up an OnClickListener for the capturePhotoButton
        capturePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        // Set up an OnClickListener for the saveButton
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user-entered data
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();

                // Validate and save the contact data to the database
                if (isValidContactData(name, phone, email)) {
                    // Create a new Contact object and save it to the database
                    Contact newContact = new Contact();
                    newContact.setName(name);
                    newContact.setPhoneNumber(phone);
                    newContact.setEmail(email);
                    // Set the photo byte array here

                    // Save the contact to the database using the ContactDataSource class
                    ContactDataSource dataSource = new ContactDataSource(getActivity());
                    dataSource.open();
                    long contactId = dataSource.createContact(newContact);
                    dataSource.close();

                    // Provide positive feedback to the user
                    showToast("Contact saved successfully");

                    // Optionally, you can navigate to the contact list or perform other actions
                } else {
                    // Handle validation errors or provide feedback to the user
                    showToast("Please enter valid contact information");
                }
            }
        });

        // Set up other UI element actions and functionality here

        return rootView;
    }

    // Function to display a Toast message
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    // Validation function to check if the user-entered data is valid
    private boolean isValidContactData(String name, String phone, String email) {
        // Check for empty or null values
        if (name.trim().isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty()) {
            return false;
        }

        // Sample phone number validation (using a regular expression)
        String phonePattern = "^[2-9]\\d{2}-\\d{3}-\\d{4}$";
        if (!phone.matches(phonePattern)) {
            return false;
        }

        // Sample email validation (using a simple regex pattern)
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            return false;
        }

        // All validation checks passed, data is valid
        return true;
    }

    private void importContacts() {
        // Define the projection for the data you want to retrieve
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        // Perform a query to get the contacts data
        Cursor cursor = requireActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        if (cursor != null) {
            try {
                int displayNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
                int phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                while (cursor.moveToNext()) {
                    // Check if the columns exist before retrieving data
                    if (displayNameIndex >= 0 && emailIndex >= 0 && phoneNumberIndex >= 0) {
                        // Retrieve contact information
                        String name = cursor.getString(displayNameIndex);
                        String email = cursor.getString(emailIndex);
                        String phoneNumber = cursor.getString(phoneNumberIndex);

                        // Create a new Contact object
                        Contact newContact = new Contact();
                        newContact.setName(name);
                        newContact.setPhoneNumber(phoneNumber);
                        newContact.setEmail(email);
                        // Set the photo byte array here if needed

                        // Add the Contact object to your app's data structure or database
                        // contactList.add(newContact);
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted; proceed with contact import
                importContacts();
            } else {
                // Permission denied;
                System.out.println("Permission denied.");
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            contactPhotoImageView.setImageBitmap(imageBitmap);
        }
    }
}

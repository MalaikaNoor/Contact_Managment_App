package com.example.contact_managment_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactDataSource {
    private SQLiteDatabase database;
    private ContactDatabaseHelper dbHelper;

    public ContactDataSource(Context context) {
        dbHelper = new ContactDatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(ContactDatabaseHelper.COLUMN_NAME, contact.getName());
        values.put(ContactDatabaseHelper.COLUMN_PHONE, contact.getPhoneNumber());
        values.put(ContactDatabaseHelper.COLUMN_EMAIL, contact.getEmail());
        values.put(ContactDatabaseHelper.COLUMN_PHOTO, contact.getPhoto());

        return database.insert(ContactDatabaseHelper.TABLE_NAME, null, values);
    }

    public void updateContact(Contact contact) {
        long id = contact.getId();
        ContentValues values = new ContentValues();
        values.put(ContactDatabaseHelper.COLUMN_NAME, contact.getName());
        values.put(ContactDatabaseHelper.COLUMN_PHONE, contact.getPhoneNumber());
        values.put(ContactDatabaseHelper.COLUMN_EMAIL, contact.getEmail());
        values.put(ContactDatabaseHelper.COLUMN_PHOTO, contact.getPhoto());

        database.update(ContactDatabaseHelper.TABLE_NAME, values, ContactDatabaseHelper.COLUMN_ID + " = " + id, null);
    }

    public void deleteContact(long contactId) {
        database.delete(ContactDatabaseHelper.TABLE_NAME, ContactDatabaseHelper.COLUMN_ID + " = " + contactId, null);
    }

    public Contact getContact(long contactId) {
        Cursor cursor = database.query(ContactDatabaseHelper.TABLE_NAME, null,
                ContactDatabaseHelper.COLUMN_ID + " = " + contactId, null, null, null, null);

        Contact contact = null;
        if (cursor != null && cursor.moveToFirst()) {
            contact = cursorToContact(cursor);
            cursor.close();
        }
        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = database.query(ContactDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Contact contact = cursorToContact(cursor);
                contacts.add(contact);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return contacts;
    }

    private Contact cursorToContact(Cursor cursor) {
        Contact contact = new Contact();
        int idColumnIndex = cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_ID);

        if (idColumnIndex >= 0) {
            contact.setId(cursor.getLong(idColumnIndex));
        }

        int nameColumnIndex = cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_NAME);
        if (nameColumnIndex >= 0) {
            contact.setName(cursor.getString(nameColumnIndex));
        }

        int phoneColumnIndex = cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_PHONE);
        if (phoneColumnIndex >= 0) {
            contact.setPhoneNumber(cursor.getString(phoneColumnIndex));
        }

        int emailColumnIndex = cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_EMAIL);
        if (emailColumnIndex >= 0) {
            contact.setEmail(cursor.getString(emailColumnIndex));
        }

        int photoColumnIndex = cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_PHOTO);
        if (photoColumnIndex >= 0) {
            contact.setPhoto(cursor.getBlob(photoColumnIndex));
        }

        return contact;
    }
}

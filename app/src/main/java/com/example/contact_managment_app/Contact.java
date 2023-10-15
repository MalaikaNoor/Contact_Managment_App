package com.example.contact_managment_app;

public class Contact {
    private long id; // Unique identifier for the contact
    private String name;
    private String phoneNumber;
    private String email;
    private byte[] photo; // Store photos as byte arrays

    // Constructors
    public Contact() {
        // Default constructor
        Contact newContact = new Contact(); // Create a new Contact object with default values
        newContact.setName("John Doe");      // Set the name
        newContact.setPhoneNumber("123-456-7890"); // Set the phone number
        newContact.setEmail("john@example.com");
    }

    public Contact(long id, String name, String phoneNumber, String email, byte[] photo) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.photo = photo;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}



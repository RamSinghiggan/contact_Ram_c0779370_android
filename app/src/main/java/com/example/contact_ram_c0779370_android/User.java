package com.example.contact_ram_c0779370_android;

public class User {
    int id;
    String firstname, lastName,Email,Address;
    int phone;

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddress() {
        return Address;
    }

    public int getPhone() {
        return phone;
    }

    public User(int id, String firstname, String lastName, String email, String address, int phone) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.Email = email;
        Address = address;
        this.phone = phone;
    }
}

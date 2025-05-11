package com.example.myapplication;

public class User {
    public String name, phone, email, imageUrl;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {}

    public User(String name, String phone, String email,String imageUrl) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.example.gimmedagame.models;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class Poster {

    private String title;
    private String userName;
    private String phone;
    private String email;
    private String location;
    private double price;
    private String description;
    private Date postDate;

    public Poster(){}

    public Poster(String title, String phone, String email, String location, double price, String description) {
        this.title = title;
        this.userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.price = price;
        this.description = description;
        this.postDate = new Date();
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

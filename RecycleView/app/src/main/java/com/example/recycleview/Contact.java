package com.example.recycleview;

public class Contact {
    private String name;
    private String phone;
    private String imageUrl;
    private String location;

    public Contact(String name, String phone, String imageUrl, String location) {
        this.name = name;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.location = location;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

package com.example.googlemapdemo.model;

public class Apartment {
    private String address, owner, phone;
    private double latitude, longtitue, price;

    public Apartment() {
    }

    public Apartment(String address, String owner, String phone, double latitude, double longtitue, double price) {
        this.address = address;
        this.owner = owner;
        this.phone = phone;
        this.latitude = latitude;
        this.longtitue = longtitue;
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitue() {
        return longtitue;
    }

    public void setLongtitue(double longtitue) {
        this.longtitue = longtitue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

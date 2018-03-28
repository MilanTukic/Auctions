package com.example.tukicmilan.auctions.milan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TukicMilan on 1/22/2018.
 */

public class User implements Serializable {

    private long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String picture;
    private List<Auction> auctions;
    private List<Bid> bids;

    public User(long id, String name, String email, String password, String picture, String address, String phone, List<Auction> auctions, List<Bid> bids) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.address = address;
        this.phone = phone;
        this.auctions = auctions;
        this.bids = bids;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", picture='" + picture + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", auctions=" + auctions +
                ", bids=" + bids +
                '}';
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}

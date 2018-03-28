package com.example.tukicmilan.auctions.milan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TukicMilan on 1/22/2018.
 */

public class Bid implements Serializable {

    private long id;
    private int price;
    private String dateTime;
    private Auction auction;
    private Item item;
    private User user;

    @Override
    public String toString() {
        return "Bid{" +
                "item=" + item +
                '}';
    }
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Bid(Item item) {
        this.item = item;
    }

    public Bid(long id, int price, String dateTime, Auction auction, User user) {
        this.id = id;
        this.price = price;
        this.dateTime = dateTime;
        this.auction = auction;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


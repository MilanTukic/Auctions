package com.example.tukicmilan.auctions.milan.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

/**
 * Created by TukicMilan on 1/22/2018.
 */

public class Item implements Serializable {

    private long id;
    private String name;
    private String description;
    private String picture;
    private boolean sold = false;
    private List<Auction> auctions;

    public Item() {
    }

    public Item(String name, String description, String picture, boolean sold, List<Auction> auctions, long id) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.sold = sold;
        this.auctions = auctions;
        this.id = id;

    }
    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", sold=" + sold +
                ", auctions=" + auctions +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

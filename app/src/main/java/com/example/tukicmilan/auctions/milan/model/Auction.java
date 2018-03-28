package com.example.tukicmilan.auctions.milan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TukicMilan on 1/22/2018.
 */

public class Auction implements Serializable {

    private long id;
    private int startPrice;
    private String startDate;
    private String endDate;
    private User user;
    private Item item;
    private ArrayList<Bid> bids;

    public Auction(long id) {
        this.id = id;
    }
    public Auction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "startPrice=" + startPrice +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", user=" + user +
                ", item=" + item +
                ", bids=" + bids +
                ", id=" + id +
                '}';
    }

    /**
     *
     * @param startPrice pocetna cena
     * @param startDate Datum pocetka aukcije
     * @param endDate Datum zavrsetka aukcije
     * @param user Id korisnika
     * @param item Id Itema
     * @param bids lista ponuda
     * @param id id aukcije
     */
    public Auction(int startPrice, String startDate, String endDate, User user, Item item, ArrayList<Bid> bids, long id) {
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.item = item;
        this.bids = bids;
        this.id = id;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public void setBids(ArrayList<Bid> bids) {
        this.bids = bids;
    }
}

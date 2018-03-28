package com.example.tukicmilan.auctions.milan.model;

/**
 * Created by TukicMilan on 2/21/2018.
 */

public class Article {

    private String title, genre, year;

    public Article() {
    }

    public Article(String title, String genre, String year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

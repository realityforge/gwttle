package com.mySampleApplication.shared;

import java.io.Serializable;

public class Book implements Serializable {
    public String ID;
    public String title;
    public String genre;
    public String author;

    protected Book() {
        this(null, null, null, null);
    }

    public Book(String ID, String title, String genre, String author) {
        this.ID = ID;
        this.title = title;
        this.genre = genre;
        this.author = author;
    }
}

package com.mySampleApplication.shared;

import de.novanic.eventservice.client.event.Event;

import java.util.Collection;

public class BooksUpdateEvent implements Event {
    private Collection<Book> books;

    protected BooksUpdateEvent() {
        this(null);
    }

    public BooksUpdateEvent(Collection<Book> books) {
        this.books = books;
    }

    public Collection<Book> getBooks() {
        return books;
    }
}

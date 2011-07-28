package com.mySampleApplication.shared;

import java.util.Collection;

public class ListBooksResponse implements Response {
    private Collection<Book> books;

    protected ListBooksResponse() {
        this(null);
    }

    public ListBooksResponse(Collection<Book> books) {
        this.books = books;
    }

    public Collection<Book> getBooks() {
        return books;
    }
}

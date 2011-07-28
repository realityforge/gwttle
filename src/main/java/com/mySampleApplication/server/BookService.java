package com.mySampleApplication.server;

import com.mySampleApplication.shared.Book;

import java.util.Collection;

public interface BookService {
    void addBook(Book book);

    void removeBook(String bookID);

    Book getBookByID(String bookID);

    Book getBookByTitle(String title);

    Collection<Book> listBooks();
}

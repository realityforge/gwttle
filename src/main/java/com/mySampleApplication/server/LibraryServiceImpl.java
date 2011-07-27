package com.mySampleApplication.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mySampleApplication.shared.Book;
import com.mySampleApplication.shared.LibraryService;

import java.util.ArrayList;
import java.util.Collection;

public class LibraryServiceImpl extends RemoteServiceServlet implements LibraryService {
    public void addBook(Book book) {
        BookServiceFactory.addBook(book);
    }

    public void removeBook(String bookID) {
        BookServiceFactory.removeBook(bookID);
    }

    public Collection<Book> listBooks() {
        return BookServiceFactory.listBooks();
    }
}

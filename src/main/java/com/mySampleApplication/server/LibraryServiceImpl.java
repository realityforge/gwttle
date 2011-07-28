package com.mySampleApplication.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mySampleApplication.shared.Action;
import com.mySampleApplication.shared.Book;
import com.mySampleApplication.shared.LibraryService;
import com.mySampleApplication.shared.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class LibraryServiceImpl extends RemoteServiceServlet implements LibraryService {

    @Inject
    private BookService service;

    public void addBook(Book book) {
        service.addBook(book);
    }

    public void removeBook(String bookID) {
        service.removeBook(bookID);
    }

    public Collection<Book> listBooks() {
        return service.listBooks();
    }

    public <T extends Response> T execute(Action<T> action) {
        return action.execute(this);
    }
}

package com.mySampleApplication.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Collection;

public interface LibraryServiceAsync {
    void addBook(Book book, AsyncCallback<Void> async);

    void removeBook(String bookID, AsyncCallback<Void> async);

    void listBooks(AsyncCallback<Collection<Book>> async);

    <T extends Response> void execute(Action<T> action, AsyncCallback<T> async);
}

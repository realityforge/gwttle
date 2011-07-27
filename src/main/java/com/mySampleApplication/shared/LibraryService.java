package com.mySampleApplication.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;

@RemoteServiceRelativePath("Library")
public interface LibraryService extends RemoteService {

    void addBook(Book book);

    void removeBook(String bookID);

    Collection<Book> listBooks();
}

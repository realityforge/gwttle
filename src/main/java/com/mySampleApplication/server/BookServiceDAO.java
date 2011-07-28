package com.mySampleApplication.server;

import com.mySampleApplication.shared.Book;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;

@Singleton
public class BookServiceDAO implements BookService {
    private final ArrayList<Book> books = new ArrayList<Book>();

    {
        addBook(new Book(null, "Monkey Flight Control", "Pseudo-Science", "Ted"));
        addBook(new Book(null, "Monkey baiting", "Science", "James"));
    }

    private int id = 1;

    @Override
    public void addBook(Book book) {
        final Book newBook = new Book("" + (id++), book.title, book.genre, book.author);
        books.add(newBook);
    }

    @Override
    public void removeBook(String bookID) {
        final Book foundBook = getBookByID(bookID);
        if (null != foundBook) {
            books.remove(foundBook);
        }
    }

    @Override
    public Book getBookByID(String bookID) {
        for (final Book book : books) {
            if (book.ID.equals(bookID)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        for (final Book book : books) {
            if (book.title.equals(title)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public Collection<Book> listBooks() {
        return books;
    }
}

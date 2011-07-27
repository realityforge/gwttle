package com.mySampleApplication.server;

import com.mySampleApplication.shared.Book;

import java.util.ArrayList;
import java.util.Collection;

public class BookServiceFactory {
    private static final ArrayList<Book> books = new ArrayList<Book>();

    static
    {
        addBook(new Book(null, "Monkey Flight Control", "Pseudo-Science", "Ted"));
        addBook(new Book(null, "Monkey baiting", "Science", "James"));
    }

    private static int id = 1;

    public static void addBook(Book book) {
        final Book newBook = new Book("" + (id++), book.title, book.genre, book.author);
        books.add(newBook);
    }

    public static void removeBook(String bookID) {
        Book foundBook = null;
        for (final Book book : books) {
            if (book.ID.equals(bookID)) {
                foundBook = book;
                break;
            }
        }
        if (null != foundBook) {
            books.remove(foundBook);
        }
    }

    public static Collection<Book> listBooks() {
        return books;
    }
}

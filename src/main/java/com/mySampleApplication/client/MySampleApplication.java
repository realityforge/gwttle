package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

import java.util.ArrayList;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {

    public static final ArrayList<Book> BOOKS = new ArrayList<Book>();

    public static class Book {
        final String title;
        final String genre;
        final String author;

        public Book(String title, String genre, String author) {
            this.title = title;
            this.genre = genre;
            this.author = author;
        }
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        final VerticalPanel verticalPanel = new VerticalPanel();
        final FlexTable t = new FlexTable();
        t.setText(0, 0, "Title");
        t.setText(0, 1, "Genre");
        t.setText(0, 2, "Author");
        t.setText(0, 3, "Actions");
        addBookToTable(t, new Book("Monkey baiting", "Science", "James"));
        addBookToTable(t, new Book("Monkey Flight Control", "Pseudo-Science", "Ted"));
        verticalPanel.add(t);
        final Button button = new Button("Add New");
        button.addClickHandler( new ClickHandler() {
            public void onClick(ClickEvent event) {
                final AddBookDialog box = new AddBookDialog(new BookAddedHandler() {
                    public void addBook(Book book) {
                        addBookToTable(t, book);
                    }
                });
                box.show();
            }
        });
        verticalPanel.add(button);
        RootPanel.get("book_content").add(verticalPanel);

/*

        final Button button = new Button("Click me");
        final Label label = new Label();

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (label.getText().equals("")) {
                    MySampleApplicationService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(label));
                } else {
                    label.setText("");
                }
            }
        });

        // Assume that the host HTML has elements defined whose
        // IDs are "slot1", "slot2".  In a real app, you probably would not want
        // to hard-code IDs.  Instead, you could, for example, search for all
        // elements with a particular CSS class and replace them with widgets.
        //
        RootPanel.get("slot1").add(button);
        RootPanel.get("slot2").add(label);
*/
    }

    private void addBookToTable(final FlexTable t, final Book book) {
        BOOKS.add(book);
        final int row = BOOKS.indexOf(book) + 1;
        t.setText(row, 0, book.title);
        t.setText(row, 1, book.genre);
        t.setText(row, 2, book.author);
        final Anchor description = new Anchor("Description");
        description.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Window.alert("Book " + book.title + " is mediocre at best");

            }
        });
        final Anchor remove = new Anchor("Remove");
        remove.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                final int currentRow = BOOKS.indexOf(book);
                BOOKS.remove(book);
                Window.alert("Book " + book.title + " removed");
                t.removeRow(currentRow + 1);
            }
        });
        final HorizontalPanel panel = new HorizontalPanel();
        panel.add(description);
        panel.add(remove);
        t.setWidget(row, 3, panel);
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}

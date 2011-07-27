package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;

public class BookRepository implements EntryPoint {

    private Timer timer;

    //Not needed because it is the default
    @UiTemplate("BookRepository.ui.xml")
    interface BookRepositoryUIBinder extends UiBinder<Widget, BookRepository> {
    }

    private static final BookRepositoryUIBinder uiBinder = GWT.create(BookRepositoryUIBinder.class);

    public static final ArrayList<Book> BOOKS = new ArrayList<Book>();

    @UiField
    FlexTable data;

    @UiField
    HasClickHandlers createNewButton;

    public void onModuleLoad() {

        RootPanel.get("book_content").add(uiBinder.createAndBindUi(this));
        setupTableData();
        timer = new Timer() {
            @Override
            public void run() {
                BookRepository.alert("Quickly now!");
            }
        };
        timer.schedule(5000);
    }

    @SuppressWarnings({"UnusedParameters"})
    @UiHandler("createNewButton")
    public void onClick(final ClickEvent event) {
        final AddBookDialog box = new AddBookDialog(new BookAddedHandler() {
            public void addBook(Book book) {
                addBookToTable(data, book);
                timer.cancel();
            }
        });
        box.center();
    }

    private void setupTableData() {
        data.setText(0, 0, "Title");
        data.setText(0, 1, "Genre");
        data.setText(0, 2, "Author");
        data.setText(0, 3, "Actions");
        addBookToTable(data, new Book("Monkey baiting", "Science", "James"));
        addBookToTable(data, new Book("Monkey Flight Control", "Pseudo-Science", "Ted"));
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

    /**
     * Example use of JSNI but DON'T do this at home kids.
     */
    public static native void alert(final String message) /*-{
        $wnd.alert(message)
    }-*/;
}

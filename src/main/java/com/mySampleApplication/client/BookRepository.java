package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.mySampleApplication.shared.Book;
import com.mySampleApplication.shared.LibraryService;
import com.mySampleApplication.shared.LibraryServiceAsync;

import java.util.Collection;

public class BookRepository implements EntryPoint {

    private Timer timer;

    //Not needed because it is the default
    @UiTemplate("BookRepository.ui.xml")
    interface BookRepositoryUIBinder extends UiBinder<Widget, BookRepository> {
    }

    private static final BookRepositoryUIBinder uiBinder = GWT.create(BookRepositoryUIBinder.class);

    private static final LibraryServiceAsync libraryServices = GWT.create(LibraryService.class);

    @UiField
    FlexTable data;

    @UiField
    HasClickHandlers createNewButton;

    public void onModuleLoad() {

        RootPanel.get("book_content").add(uiBinder.createAndBindUi(this));
        rebuildTable();
        timer = new Timer() {
            @Override
            public void run() {
                BookRepository.alert("Quickly now!");
            }
        };
        timer.schedule(5000 * 100);
    }

    @SuppressWarnings({"UnusedParameters"})
    @UiHandler("createNewButton")
    public void onClick(final ClickEvent event) {
        final AddBookDialog box = new AddBookDialog(new BookAddedHandler() {
            public void addBook(final Book book) {
                libraryServices.addBook(book, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        BookRepository.alert("Danger Will Robinson!");
                    }

                    public void onSuccess(Void result) {
                        addBookToTable(data, book, data.getRowCount());
                    }
                });
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
    }

    private void rebuildTable() {
        libraryServices.listBooks(new AsyncCallback<Collection<Book>>() {
            public void onFailure(Throwable caught) {
                BookRepository.alert("Danger Will Robinson!");
            }

            public void onSuccess(Collection<Book> result) {
                data.removeAllRows();
                setupTableData();
                final Book[] bookList = result.toArray(new Book[result.size()]);
                for (int i = 0; i < bookList.length; i++) {
                    addBookToTable(data, bookList[i], i + 1);
                }
            }
        });
    }

    private void addBookToTable(final FlexTable t, final Book book, final int row) {

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
                libraryServices.removeBook(book.ID, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        BookRepository.alert("Danger Will Robinson!");
                    }

                    public void onSuccess(Void result) {
                        Window.alert("Book " + book.title + " removed");
                        t.removeRow(row);
                    }
                });
            }
        });

        final Anchor review = new Anchor("See Review");
        review.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                final RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode("http://127.0.0.1:8888/BookRepository/review/" + book.title));
                try {
                    final Request request = builder.sendRequest(null, new RequestCallback() {
                        public void onResponseReceived(com.google.gwt.http.client.Request request, Response response) {
                            final Document document = XMLParser.parse(response.getText());
                            final Element documentElement = document.getDocumentElement();
                            final Node item = documentElement.getElementsByTagName("review-text").item(0);
                            final String value = item.getFirstChild().getNodeValue();
                            Window.alert(value);
                        }

                        public void onError(com.google.gwt.http.client.Request request, Throwable exception) {
                            BookRepository.alert("Danger Will Robinson!");
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
        final HorizontalPanel panel = new HorizontalPanel();
        panel.add(description);
        panel.add(remove);
        panel.add(review);
        t.setWidget(row, 3, panel);
    }

    /**
     * Example use of JSNI but DON'T do this at home kids.
     */
    public static native void alert(final String message) /*-{
        $wnd.alert(message)
    }-*/;
}

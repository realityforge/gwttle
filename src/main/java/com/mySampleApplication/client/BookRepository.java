package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
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
import com.mySampleApplication.shared.*;
import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;
import net.customware.gwt.presenter.client.DefaultEventBus;

import java.util.Collection;

public class BookRepository implements EntryPoint {

    private Timer timer;

    //Not needed because it is the default
    @UiTemplate("BookRepository.ui.xml")
    interface BookRepositoryUIBinder extends UiBinder<Widget, BookRepository> {
    }

    private static final BookRepositoryUIBinder uiBinder = GWT.create(BookRepositoryUIBinder.class);

    private static final LibraryServiceAsync libraryServices = GWT.create(LibraryService.class);
    private static final PollerServiceAsync pollerService = GWT.create(PollerService.class);

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

        RemoteEventServiceFactory.getInstance().getRemoteEventService().
                addListener(DomainFactory.getDomain("Magic"), new RemoteEventListener() {
                    @Override
                    public void apply(final Event event) {
                        final BooksUpdateEvent e = (BooksUpdateEvent) event;
                        rebuildTableFromData(e.getBooks());
                    }
                });
        pollerService.letThereBeBooks(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Oh Dash!");
            }

            @Override
            public void onSuccess(Void result) {
                Window.alert("Hoorah!");
            }
        });
    }

    @SuppressWarnings({"UnusedParameters"})
    @UiHandler("createNewButton")
    public void onClick(final ClickEvent event) {

        final DefaultEventBus eventBus = new DefaultEventBus();
        eventBus.addHandler(RequestBookAddEvent.TYPE, new RequestBookAddEvent.RequestBookAddEventHandler() {
            @Override
            public void requestBook(final Book book) {
                libraryServices.execute(new AddBookAction(book), new AsyncCallback<AddBookResponse>() {
                    public void onFailure(Throwable caught) {
                        BookRepository.alert("Danger Will Robinson!");
                    }

                    public void onSuccess(AddBookResponse result) {
                        addBookToTable(data, book, data.getRowCount());
                    }
                });
                timer.cancel();
            }
        });
        final AddBookPresenter presenter = new AddBookPresenter(new AddBookDialog(), eventBus);
        presenter.onBind();
    }

    private void setupTableData() {
        data.setText(0, 0, "Title");
        data.setText(0, 1, "Genre");
        data.setText(0, 2, "Author");
        data.setText(0, 3, "Actions");
    }

    private void rebuildTable() {
        libraryServices.execute(new ListBooksAction(), new AsyncCallback<ListBooksResponse>() {
            public void onFailure(Throwable caught) {
                BookRepository.alert("ListBooksAction: Danger Will Robinson!");
            }

            public void onSuccess(final ListBooksResponse result) {
                rebuildTableFromData(result.getBooks());
            }
        });
    }

    private void rebuildTableFromData(Collection<Book> books) {
        data.removeAllRows();
        setupTableData();
        final Book[] bookList = books.toArray(new Book[books.size()]);
        for (int i = 0; i < bookList.length; i++) {
            addBookToTable(data, bookList[i], i + 1);
        }
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
                libraryServices.execute(new RemoveBookAction(book.ID), new AsyncCallback<RemoveBookResponse>() {
                    public void onFailure(Throwable caught) {
                        BookRepository.alert("Danger Will Robinson!");
                    }

                    public void onSuccess(final RemoveBookResponse result) {
                        Window.alert("Book " + book.title + " removed");
                        t.removeRow(row);
                    }
                });
            }
        });

        final Anchor review = new Anchor("See Review");
        review.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                final RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(GWT.getModuleBaseURL() + "review/" + book.title));
                try {
                    builder.sendRequest(null, new RequestCallback() {
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

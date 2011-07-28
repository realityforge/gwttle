package com.mySampleApplication.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.mySampleApplication.shared.Book;

public class RequestBookAddEvent extends GwtEvent<RequestBookAddEvent.RequestBookAddEventHandler> {

    public interface RequestBookAddEventHandler extends EventHandler {
        void requestBook(Book book);
    }

    public static final GwtEvent.Type<RequestBookAddEventHandler> TYPE = new Type<RequestBookAddEventHandler>();

    private Book book;

    public RequestBookAddEvent(Book book) {
        this.book = book;
    }

    @Override
    public Type<RequestBookAddEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RequestBookAddEventHandler handler) {
        handler.requestBook(book);
    }
}

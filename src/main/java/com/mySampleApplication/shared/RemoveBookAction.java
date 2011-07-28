package com.mySampleApplication.shared;

public class RemoveBookAction implements Action<RemoveBookResponse> {
    private String bookID;

    public RemoveBookAction() {
        this(null);
    }

    public RemoveBookAction(String bookID) {
        this.bookID = bookID;
    }

    public RemoveBookResponse execute(final LibraryService service) {
        service.removeBook(bookID);
        return new RemoveBookResponse();
    }
}

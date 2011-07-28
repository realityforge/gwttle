package com.mySampleApplication.shared;

public class AddBookAction implements Action<AddBookResponse> {
    private Book book;

    public AddBookAction() {
        this(null);
    }

    public AddBookAction(Book book) {
        this.book = book;
    }

    public AddBookResponse execute(final LibraryService service) {
        service.addBook(book);
        return new AddBookResponse();
    }
}

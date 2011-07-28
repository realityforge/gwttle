package com.mySampleApplication.shared;

public class ListBooksAction implements Action<ListBooksResponse> {

    public ListBooksResponse execute(final LibraryService service) {
        return new ListBooksResponse(service.listBooks());
    }
}

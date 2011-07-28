package com.mySampleApplication.server;

import com.google.inject.AbstractModule;

public class BookComponentsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BookService.class).to(BookServiceDAO.class);
    }
}

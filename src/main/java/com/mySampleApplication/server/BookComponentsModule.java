package com.mySampleApplication.server;

import com.google.inject.AbstractModule;

public class BookComponentsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BookService.class).to(BookServiceDAO.class);

        //bind(BookService.class).toProvider(fromJNDI(BookService.class, "ejb/MyEJB");
        //bind(BookService.class).to(BookServiceDAO.class).toProvider(com.google.inject.name.Names.named("Foo");
    }
}

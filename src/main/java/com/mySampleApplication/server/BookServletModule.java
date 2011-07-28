package com.mySampleApplication.server;

import com.google.inject.servlet.ServletModule;
import de.novanic.eventservice.service.EventServiceImpl;

public class BookServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        serve("/BookRepository/Library").with(LibraryServiceImpl.class);
        serve("/BookRepository/review/*").with(BookReviewService.class);
        serve("/BookRepository/Poller").with(PushServlet.class);
        //serve("/BookRepository/gwteventservice").with(EventServiceImpl.class);
    }
}

package com.mySampleApplication.server;

import com.mySampleApplication.shared.Book;
import com.mySampleApplication.shared.BooksUpdateEvent;
import com.mySampleApplication.shared.PollerService;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.service.RemoteEventServiceServlet;

public class PushServlet
        extends RemoteEventServiceServlet
        implements Runnable, PollerService {

    public void run() {

        for( int i = 0; i < 4; i++ )
        {
            try {
                Thread.sleep(5000);
                BookServiceFactory.addBook(new Book(null, "Book " + i + " Time = " + System.currentTimeMillis(), " blah", "blee"));
                addEvent(DomainFactory.getDomain("Magic"), new BooksUpdateEvent(BookServiceFactory.listBooks()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void letThereBeBooks() {
        new Thread(this).start();
    }
}

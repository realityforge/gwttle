package com.mySampleApplication.server;

import com.mySampleApplication.shared.Book;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.PrintWriter;
import java.net.URLDecoder;

@Singleton
public class BookReviewService extends HttpServlet {

    @Inject
    private BookService service;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            final String uri = request.getRequestURI();
            final String bookTitle = URLDecoder.decode(uri.substring(uri.lastIndexOf("/") + 1, uri.length()));

            final Book book = service.getBookByTitle(bookTitle);
            if (null != book) {
                final BookReview review = new BookReview(book.title, book.author, "Love it!");
                final PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                final Marshaller m = JAXBContext.newInstance(BookReview.class).createMarshaller();
                m.marshal(review, out);
            }
        } catch (final Exception e) {
            throw new ServletException(e);
        }
    }
}

package com.mySampleApplication.server;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings({"UnusedDeclaration"})
@XmlRootElement(name = "review")
public class BookReview {

    private String bookTitle;
    private String review;
    private String reviewer;

    public BookReview() {
    }

    public BookReview(String title, String reviewer, String review) {
        this.bookTitle = title;
        this.reviewer = reviewer;
        this.review = review;
    }

    @XmlAttribute(name = "title")
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @XmlElement(name = "review-text")
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @XmlAttribute(name = "reviewer")
    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

}

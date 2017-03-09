package com.aasavari.booklistingapp;

/**
 * Created by Aasavari on 2/28/2017.
 */

public class Book {

    private String mAuthor;
    private String mTitle;

    public Book(String author, String title){
        mAuthor = author;
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }
}

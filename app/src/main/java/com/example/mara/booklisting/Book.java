package com.example.mara.booklisting;

/**
 * Created by Mara on 23.6.2017..
 */

public class Book {

    //Create a variable for the name of the book
    private String mName;

    //Create a variable for the author of the book
    private String mAuthor;

    //Create a variable for the description of the book
    private String mDescription;

    //Create a variable for the url of the book
    private String mUrl;

    /* The public constructor of the Book Object
     * @param author: the author of the book
     * @param name: the name of the book
     * @param description: the description of the book
     * @param link: the info link of the book
     */
    public Book(String name, String author, String description, String url) {
        mName = name;
        mAuthor = author;
        mDescription = description;
        mUrl = url;
    }

    //Returns the name of the book
    public String getName() {
        return mName;
    }

    //Returns the author of the book
    public String getAuthor() {
        return mAuthor;
    }

    //Returns the description of the book
    public String getDescription() {
        return mDescription;
    }

    //Returns the url of the book
    public String getUrl() {
        return mDescription;
    }
}

package com.humble.booksearch;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Books {

    private String title;
    private JSONArray authors;
    private String publisher;
    private String publishing_date;
    private String description;


    public Books(String t, @Nullable JSONArray au,@Nullable String pu,@Nullable String pu_d,@Nullable String des){
        title = t;
        authors = au;
        publisher = pu;
        publishing_date = pu_d;
        description = des;


    }


    public JSONArray getAuthors() {
        return authors;
    }


    public String getDescription() {
        return description;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishing_date() {
        return publishing_date;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthors(JSONArray authors) {
        this.authors = authors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublishing_date(String publishing_date) {
        this.publishing_date = publishing_date;
    }


    public void setTitle(String title) {
        this.title = title;
    }

}

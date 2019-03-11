package com.humble.booksearch;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LoaderActivity {

    public static final String LOG_TAG = LoaderActivity.class.getSimpleName();

    public  static List<Books> extactBook(String url){
           //createURL
              URL data = createURL(url);
           //makeHttpRequest(raise IO Exception)
              String jsonResp = makeHttpRequest(data);
           //extract json (Raise JSONException)
        List<Books> booksList = null;
        try {
            booksList = extractdata(jsonResp);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"Error getting the JSON Response");
        }

        return  booksList;

    }

    //createURL function

    public static URL createURL(String u){
        //Initialize the URL object
        URL url_new = null;
        try {
            url_new = new URL(u);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"Please enter a correct Url");
        }

        return url_new;
    }

    public static String makeHttpRequest(URL dat){
        HttpURLConnection new_connection = null;
        InputStream new_stream = null;
        String jsonResponse = null;

        if(dat == null){
            return jsonResponse;
        }

        try{
            new_connection = (HttpURLConnection) dat.openConnection();
            new_connection.setConnectTimeout(10000);
            new_connection.setReadTimeout(15000);
            new_connection.setRequestMethod("GET");
            new_connection.connect();

            if(new_connection.getResponseCode() == 200){
                new_stream = new_connection.getInputStream();
                jsonResponse = extractJSON(new_stream);//seperate method to get the JSON from the
                                                       //InputStream which gives the data in the form of datum
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"There is a problem getting the InputStream or Http Request");
        } finally {
            if(new_connection != null){
                new_connection.disconnect();
            }
            if (new_stream != null){
                try {
                    new_stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG,"Error closing the Input Stream");
                }
            }
            }

            return  jsonResponse;
    }

    public static String extractJSON(InputStream dataStream) throws IOException {
        String line = "";
        StringBuilder builder = new StringBuilder();
        if(dataStream != null){
            InputStreamReader datareader = new InputStreamReader(dataStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(datareader);
            line = reader.readLine();
            while(line != null){
                builder.append(line);
                line = reader.readLine();
            }}
            else {
            Log.e(LOG_TAG,"Input Stream not available");
            }

            return  builder.toString();

        }

     public static List<Books> extractdata(String json) throws JSONException {
         ArrayList<Books> books = new ArrayList<>();

         Books new_item = null;
         JSONObject jsonObject = new JSONObject(json);
         JSONArray jsonArray = jsonObject.getJSONArray("items");
         for (int i = 0; i < jsonArray.length();i++){
             JSONObject item = (JSONObject) jsonArray.get(i);
             JSONObject volume = (JSONObject) item.get("volumeInfo");
             new_item = new Books(volume.optString("title"), volume.optJSONArray("authors"),volume.optString("publisher"),volume.optString("publishedDate"),volume.optString("description"));
             books.add(new_item);
             }
          return books;


     }



    }




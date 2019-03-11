package com.humble.booksearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class BookAdapter extends ArrayAdapter {
    String next;
    public BookAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Books currentBook = (Books) getItem(position);
        TextView author = (TextView) listItemView.findViewById(R.id.authors);
        TextView name = (TextView) listItemView.findViewById(R.id.book_name);
        TextView Publisher = (TextView) listItemView.findViewById(R.id.publishers_detail);
        TextView publisher_date = (TextView) listItemView.findViewById(R.id.date_published);
        TextView description = (TextView) listItemView.findViewById(R.id.description);
        String authors = "";
        JSONArray authorsList = currentBook.getAuthors();
        if(authorsList != null){
        for(int i = 0; i < authorsList.length();i++){
            try {
                 if(i != authorsList.length() -1){
                 next = authorsList.getString(i) + ", " ;}
                 else{
                     next = authorsList.getString(i);
                 }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Adapter","Problem parsing JSON");
                }

            authors = authors + next ;

        }} else{
            authors = "";
        }
        author.setText(authors);
        name.setText(currentBook.getTitle());
        Publisher.setText(currentBook.getPublisher());
        publisher_date.setText(currentBook.getPublishing_date());
        description.setText(currentBook.getDescription());

        return listItemView;
    }
}

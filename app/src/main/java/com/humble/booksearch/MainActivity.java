package com.humble.booksearch;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity   {
    //define the url
    private static final String extract_url = "https://www.googleapis.com/books/v1/volumes?" ;
    private static String url;
    //define all the variables required
    ArrayList<Books> Books1;
    ListView listView;
    BookAdapter adapter;
    EditText search;
    List<Books> books;
    String query;
    ImageView image;
    TextView text;

    //define the BooksLoader extending AsyncTaskLoader<Books>
    public static class BooksLoader extends AsyncTaskLoader<List<Books>>{

        public BooksLoader(Context context,String s) {
            super(context);
            url = s;

        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public List<Books> loadInBackground() {
            if(url == null){
                return null;
            }
            List<Books> books = LoaderActivity.extactBook(url);
            return books;
        }

        @Override
        public void deliverResult(List<Books> data) {
            super.deliverResult(data);
        }
    }

    LoaderManager.LoaderCallbacks<List<Books>> callbacks;
    public void Search_Button(View view) {
        query = search.getText().toString();
        if (query.toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter a Keyword", Toast.LENGTH_LONG).show();
        } else {

            callbacks = new LoaderManager.LoaderCallbacks<List<Books>>() {
                @Override
                public Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
                    Log.v("Log tag", "onCreate Loader is Called!");
                    Uri baseUrl = Uri.parse(extract_url);
                    Uri.Builder builder = baseUrl.buildUpon();

                    builder.appendQueryParameter("q", query);
                    builder.appendQueryParameter("maxResults", "40");
                    Log.v("Log", builder.toString());

                    return new BooksLoader(MainActivity.this, builder.toString());
                }

                @Override
                public void onLoaderReset(Loader<List<Books>> loader) {
                    Log.v("Log Tag", "Loader Reset is called");
                    adapter = null;

                }

                @Override
                public void onLoadFinished(Loader<List<Books>> loader, List<Books> books) {
                    Log.v("Log tag", "onLoadFinished called" + " " + query);
                    Books1 = (ArrayList<Books>) books;
                    adapter = new BookAdapter(MainActivity.this, Books1);

                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            View currentView = view;
                            TextView description = (TextView) view.findViewById(R.id.description);
                            if (description.getVisibility() == View.VISIBLE) {
                                description.setVisibility(View.GONE);
                            } else {
                                description.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }


            };


            getLoaderManager().restartLoader(0, null, callbacks);

        }

    }















    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (EditText) findViewById(R.id.search_query);
        listView = findViewById(R.id.list_view);
        Toast.makeText(MainActivity.this,"Enter your Search",Toast.LENGTH_LONG).show();
        adapter = null;
        image = findViewById(R.id.imageView4);
        listView.setEmptyView(image);
















    }
}

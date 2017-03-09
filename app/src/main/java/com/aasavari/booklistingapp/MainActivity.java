package com.aasavari.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.R.attr.data;
import static com.aasavari.booklistingapp.R.id.bookList;
import static com.aasavari.booklistingapp.R.id.searchBtn;
import static com.aasavari.booklistingapp.R.id.searchTxt;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();
    EditText mSearchTxt;
    Button mSearchBtn;
    private BookAdapter mBookAdapter;
    private ListView mBookListView;
    private ArrayList<Book> mBookList;
    private TextView mEmptyStateView;
    private String mQueryString;

    private static final String GOOGLE_BOOKS_QUERY_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=10&q=";

    private class BookListAsyncTask extends AsyncTask<String, Void, List<Book>>{


        @Override
        protected List<Book> doInBackground(String... urls) {
            //here we execute the network request
            // Don't perform the request if there are no URLs, or the first URL is null.
            if(urls.length <1 || urls[0] == null)
                return null;
            List<Book> result = QueryUtils.fetchBookData(urls[0]);
            return result;
        }


        @Override
        protected void onPostExecute(List<Book> data) {
            // If there is a valid list of {@link Book}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if(data != null && !data.isEmpty()) {
                updateUI(data);
            }
            else
                mEmptyStateView.setText(R.string.no_books);
        }

    } // inner class BookListAsyncTask

    private void updateUI(List<Book> data){
        mBookAdapter.clear();
        //add all the list items to the adapter to be viewed in the listview
        mBookAdapter.addAll(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchTxt = (EditText)findViewById(searchTxt);
        mSearchBtn = (Button)findViewById(searchBtn);
        mEmptyStateView = (TextView)findViewById(R.id.empty_state);
        mEmptyStateView.setText(R.string.how_to);

        mBookList = new ArrayList<>();
        // Find a reference to the {@link ListView} in the layout
        mBookListView = (ListView)findViewById(bookList);
        //Create a new adapter that takes a list of books as input
        mBookAdapter = new BookAdapter(this, mBookList);
        //Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        mBookListView.setAdapter(mBookAdapter);

        mBookListView.setEmptyView(mEmptyStateView);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected){

                    //form a query String based on the user's input in the edit text box
                     mQueryString = GOOGLE_BOOKS_QUERY_URL + mSearchTxt.getText().toString().trim();
                      BookListAsyncTask bookListTask = new BookListAsyncTask();
                      bookListTask.execute(mQueryString);
            }
                else
                    mEmptyStateView.setText(R.string.no_conn);
            }
        });
    }


}

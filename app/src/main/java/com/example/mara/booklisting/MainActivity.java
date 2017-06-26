package com.example.mara.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * URL for books data from Google Books API
     */
    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=android";

    /**
     * Constant value for the book loader ID.
     */
    private static final int BOOK_LOADER_ID = 1;

    /**
     * Adapter for the list of books
     **/
    private BookAdapter mBookAdapter;

    /**
     * SearchView that takes the query
     */
    private SearchView searchView;

    /**
     * List of books
     */
    private ListView booksListView;

    /**
     * Value for search query
     */
    private String mQuery;

    /**
     * TextView which displays when the list is empty
     **/
    private TextView mEmptyStateTextView;

    /**
     * ProgressBar that display when the data is loading
     **/
    private ProgressBar loadingIndicator;

    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link SearchView} in the layout
        searchView = (SearchView) findViewById(R.id.text_search);
        // Find a reference to the {@link ListView} in the layout
        booksListView = (ListView) findViewById(R.id.books_list);
        // Find a reference to the {@link ProgressBar} in the layout
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        // Find a reference to the {@link TextView} in the layout
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        // Create a new adapter that takes an empty list of books as input
        mBookAdapter = new BookAdapter(this, new ArrayList<Book>());


        //**Set the book adapter so the list can be populated in the user interface
        booksListView.setAdapter(mBookAdapter);


        loadingIndicator.setVisibility(View.GONE);

        //**Set the adapter on the {@link ListView} so the list can be populated in the user interface
        booksListView.setAdapter(mBookAdapter);
        //**get loader ony if user has searched something
        if (savedInstanceState != null) {
            mUrl = savedInstanceState.getString("URL");
        }
        if (mUrl != null) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        }
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //**Find the current book that was clicked **/
                Book currentBook = mBookAdapter.getItem(position);

                //**Convert the String URL into URI object**/
                if (currentBook != null) {
                    Uri bookUri = Uri.parse(currentBook.getUrl());
                    //**Create a new intent to view the book URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                    //**Sent the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            }
        });


        // Find the reference to the progress bar in a layout
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        // Find the reference to the empty text view in a layout and set empty view
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        booksListView.setEmptyView(mEmptyStateTextView);


        if (isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);

        } else {
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (isConnected()) {
                    booksListView.setVisibility(View.INVISIBLE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                    loadingIndicator.setVisibility(View.VISIBLE);
                    mQuery = searchView.getQuery().toString();
                    mQuery = mQuery.replace(" ", "+");
                    Log.v(LOG_TAG, mQuery);
                    getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                    searchView.clearFocus();
                } else {
                    booksListView.setVisibility(View.INVISIBLE);
                    loadingIndicator.setVisibility(View.GONE);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setText(R.string.no_internet);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // Helper method to check network connection
    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        String requestUrl = "";
        if (mQuery != null && mQuery != "") {
            requestUrl = BOOK_REQUEST_URL + mQuery;
        } else {
            String defaultQuery = "android";
            requestUrl = BOOK_REQUEST_URL + defaultQuery;
        }
        return new BookLoader(this, requestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        mEmptyStateTextView.setText(R.string.no_books);
        loadingIndicator.setVisibility(View.GONE);
        mBookAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mBookAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mBookAdapter.clear();
    }

}
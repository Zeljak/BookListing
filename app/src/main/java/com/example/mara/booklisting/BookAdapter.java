package com.example.mara.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mara on 23.6.2017..
 */

/**
 * An {@link BookAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link Book} objects).
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param books   is the list of books, which is the data source of the adapter
     */
    public BookAdapter(@NonNull Context context, @NonNull List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        //Find the book at the given position in the list of books.
        Book currentBook = getItem(position);

        // Find the TextView with the book_name ID and set the name of the current book
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.book_name);
        nameTextView.setText(currentBook.getName());

        // Find the TextView with the book_author ID and set the author of the current book
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.book_author);
        authorTextView.setText(currentBook.getAuthor());

        // Find the TextView with the book_description ID and set the description of the current book
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.book_description);
        descriptionTextView.setText(currentBook.getDescription());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }


}



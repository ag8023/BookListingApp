package com.aasavari.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by Aasavari on 2/28/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {


    public BookAdapter(Context context, List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the book object out of the arraylist given
        // to the adapter at the position passed in
        Book currBook = getItem(position);

        //if view is not from the recycle bin, then inflate a fresh listitem view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView titleView = (TextView)convertView.findViewById(R.id.book_title);
        TextView authorView = (TextView)convertView.findViewById(R.id.book_author);

        titleView.setText(currBook.getTitle());
        authorView.setText(currBook.getAuthor());

        return convertView;
    }
}

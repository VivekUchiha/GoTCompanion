package com.example.rahul.gotcompanion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahul.gotcompanion.Database.ProfileContract;
import com.squareup.picasso.Picasso;

public class ListCursorAdapter2 extends CursorAdapter {

    public ListCursorAdapter2(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView name =  view.findViewById(R.id.listName);
        ImageView image = view.findViewById(R.id.listImage);
        image.setVisibility(View.GONE);
        final String nameText = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.NAME));
        name.setText(nameText);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("current", nameText);
                context.startActivity(i);
            }
        });
    }
}


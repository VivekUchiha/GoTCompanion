package com.example.rahul.gotcompanion.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.rahul.gotcompanion.Database.DatabaseHelper;
import com.example.rahul.gotcompanion.ListCursorAdapter2;
import com.example.rahul.gotcompanion.R;
import com.example.rahul.gotcompanion.Database.ProfileContract;
import com.example.rahul.gotcompanion.ListCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public AllFragment() {
        // Required empty public constructor
    }

    private static final int FAVOURITE_LOADER = 0;
    ListView favList;
    ListCursorAdapter2 mAdapter;
    TextView empty;
    DatabaseHelper db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        db = new DatabaseHelper(getActivity());

        favList = rootView.findViewById(R.id.historyList);
        empty = rootView.findViewById(R.id.empty);
        favList.setEmptyView(empty);
        empty.setText("");

        mAdapter = new ListCursorAdapter2(getActivity(),null);
        favList.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ProfileContract.ProfileEntry.CONTENT_URI2, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater m){
        // Inflate the menu; this adds items to the action bar if it is present.
        m.inflate(R.menu.menu_search, menu);

        MenuItem search_item = menu.findItem(R.id.mi_search);

        SearchView searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(false);
        searchView.setQueryHint("Search tracks");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                Cursor c = db.getWordMatches(s,null);
                ListCursorAdapter2 Adapter = new ListCursorAdapter2(getActivity(),c);
                favList.setAdapter(Adapter);
                empty.setText("No character found.");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Cursor c = db.getWordMatches(s,null);
                ListCursorAdapter2 Adapter = new ListCursorAdapter2(getActivity(),c);
                favList.setAdapter(Adapter);
                empty.setText("No character found.");
                return false;
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}

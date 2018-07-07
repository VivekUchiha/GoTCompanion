package com.example.rahul.gotcompanion.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.rahul.gotcompanion.Database.DatabaseHelper;
import com.example.rahul.gotcompanion.ListCursorAdapter2;
import com.example.rahul.gotcompanion.ProfileActivity;
import com.example.rahul.gotcompanion.R;
import com.example.rahul.gotcompanion.Database.ProfileContract;
import com.example.rahul.gotcompanion.ListCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public HistoryFragment() {
        // Required empty public constructor
    }

    private static final int FAVOURITE_LOADER = 0;
    ListView favList;
    ListCursorAdapter mAdapter;
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
        empty.setText("Its lonely here, start searching for your favoutite characters.");

        mAdapter = new ListCursorAdapter(getActivity(),null);
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
        return new CursorLoader(getActivity(), ProfileContract.ProfileEntry.CONTENT_URI, null, null, null, ProfileContract.ProfileEntry.ID+" desc");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}

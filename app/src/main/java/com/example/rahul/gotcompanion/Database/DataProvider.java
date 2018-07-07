package com.example.rahul.gotcompanion.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class DataProvider extends ContentProvider {
    DatabaseHelper mdb;
    SQLiteDatabase database;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int PROFILE = 100;
    private static final int LIST = 102;
    private static final int PROFILE_ID = 101;

    static {
        sUriMatcher.addURI(ProfileContract.CONTENT_AUTHORITY, ProfileContract.PATH_List, LIST);
        sUriMatcher.addURI(ProfileContract.CONTENT_AUTHORITY, ProfileContract.PATH_Profile, PROFILE);
        sUriMatcher.addURI(ProfileContract.CONTENT_AUTHORITY, ProfileContract.PATH_Profile + "/#", PROFILE_ID);
    }

    /** Tag for the log messages */
    public static final String LOG_TAG = DataProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        mdb = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mdb.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PROFILE:
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                cursor = database.query(ProfileContract.ProfileEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PROFILE_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = ProfileContract.ProfileEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ProfileContract.ProfileEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case LIST:
                cursor = database.query("LIST", projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROFILE:
                return ProfileContract.ProfileEntry.CONTENT_LIST_TYPE;
            case PROFILE_ID:
                return ProfileContract.ProfileEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROFILE:
                return insertTodo(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertTodo(Uri uri, ContentValues values) {

        // PROFILE: Insert a new pet into the pets database table with the given ContentValues

        database = mdb.getWritableDatabase();
        long id = database.insert(ProfileContract.ProfileEntry.TABLE_NAME,null,values);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mdb.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case PROFILE:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(ProfileContract.ProfileEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PROFILE_ID:
                // Delete a single row given by the ID in the URI
                selection = ProfileContract.ProfileEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(ProfileContract.ProfileEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowsUpdated;
        SQLiteDatabase database = mdb.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PROFILE:
                // Delete all rows that match the selection and selection args
                rowsUpdated = database.update(ProfileContract.ProfileEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case PROFILE_ID:
                // Delete a single row given by the ID in the URI
                selection = ProfileContract.ProfileEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsUpdated = database.update(ProfileContract.ProfileEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsUpdated;
    }
}

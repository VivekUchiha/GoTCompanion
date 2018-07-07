package com.example.rahul.gotcompanion.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ProfileContract {

    private ProfileContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.rahul.gotcompanion";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_Profile = "Profile";
    public static final String PATH_List = "list";

    public static final class ProfileEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_Profile);
        public static final Uri CONTENT_URI2 = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_List);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Profile;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Profile;


        public static final String TABLE_NAME = "Profile";

        public static final String ID = BaseColumns._ID;
        public static final String NAME = "Name";
        public static final String TITLES = "titles";
        public static final String MALE = "male";
        public static final String CULTURE = "culture";
        public static final String APIID = "Api_ID";
        public static final String HOUSE = "house";
        public static final String SPOUSE = "spouse";
        public static final String RANK = "rank";
        public static final String BOOKS = "books";
        public static final String IMAGELINK = "image_link";
        public static final String SLUG = "slug";
        public static final String FAVOURITE = "favourite";

    }

}

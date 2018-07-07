package com.example.rahul.gotcompanion.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    /*Name of database*/
    public static final String DATABASE_NAME = "GOT_Database.db";
    /*database version*/
    public static final int DATABASE_VERSION = 1;


    /*Public Constructor*/
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create the table
        String SQL_CREATE_TODO_TABLE = "CREATE TABLE " + ProfileContract.ProfileEntry.TABLE_NAME + " ( "
                + ProfileContract.ProfileEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProfileContract.ProfileEntry.NAME + " TEXT NOT NULL, "
                + ProfileContract.ProfileEntry.BOOKS + " TEXT, "
                + ProfileContract.ProfileEntry.CULTURE + " TEXT, "
                + ProfileContract.ProfileEntry.APIID + " TEXT, "
                + ProfileContract.ProfileEntry.TITLES + " TEXT, "
                + ProfileContract.ProfileEntry.SPOUSE + " TEXT, "
                + ProfileContract.ProfileEntry.HOUSE + " TEXT, "
                + ProfileContract.ProfileEntry.SLUG + " TEXT, "
                + ProfileContract.ProfileEntry.MALE + " TEXT, "
                + ProfileContract.ProfileEntry.FAVOURITE + " TEXT, "
                + ProfileContract.ProfileEntry.IMAGELINK + " TEXT, "
                + ProfileContract.ProfileEntry.RANK + " INTEGER);";
        db.execSQL(SQL_CREATE_TODO_TABLE);

        String SQL_CREATE_TABLE = "CREATE TABLE LIST ( "
                + ProfileContract.ProfileEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProfileContract.ProfileEntry.NAME + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public ArrayList<String> getList(){
        ArrayList<String> list =  new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        cursor = db.rawQuery("select * from LIST;",null);

        if(cursor!=null) {
            try {
                if (cursor.getCount() != 0 && cursor.moveToFirst()) {
                    do {
                        list.add(cursor.getString(1));
                    }
                    while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return list;
    }

    public long insertData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProfileContract.ProfileEntry.NAME,name);

        long result = db.insert("LIST",null ,contentValues);

        return result;
    }

    public Cursor getWordMatches(String query, String[] columns) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = ProfileContract.ProfileEntry.NAME + " MATCH ?";
        String[] selectionArgs = new String[] {"*"+query+"*"};
        Cursor cursor = db.rawQuery("select * from list where Name like '%"+query+"%' ;", null);

        return cursor;
    }


}

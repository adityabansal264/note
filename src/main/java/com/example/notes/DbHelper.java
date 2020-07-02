package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static String TABLE_NAME = "LIST";
    public static String COL_ID = "ITEM_ID";
    public static String COL_ITEM_NAME = "ITEM_NAME";
    public static String COL_TITLE = "TITLE";


    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_ITEM_NAME + " TEXT," + COL_TITLE + " TEXT)";


    public DbHelper(Context context) {
        super(context, "note.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertData(RemainderItem item, SQLiteDatabase database) {

        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, item.title);
        cv.put(COL_ITEM_NAME, item.item_name);

        database.insert(TABLE_NAME, null, cv);
    }

    public ArrayList<RemainderItem> getData(SQLiteDatabase database) {

        ArrayList<RemainderItem> items = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                RemainderItem item = new RemainderItem();
                item.item_id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                item.title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                item.item_name = cursor.getString(cursor.getColumnIndex(COL_ITEM_NAME));

                items.add(item);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return items;


    }
}

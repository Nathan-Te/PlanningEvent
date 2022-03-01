package com.example.planningevent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eventBase";
    private static final String TABLE_EVENTS = "events";
    private static final String EVENT_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "(" + EVENT_NAME + " TEXT" + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addEvent(Evenement event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EVENT_NAME, event.getName()); // Event Name

        // Inserting Row
        db.insert(TABLE_EVENTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    Evenement getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] {EVENT_NAME }, "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Evenement event = new Evenement(cursor.getString(0));
        // return contact
        return event;
    }

    // code to get all contacts in a list view
    public List<Evenement> getAllEvents() {
        List<Evenement> eventList = new ArrayList<Evenement>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Evenement event = new Evenement();
                event.setName(cursor.getString(0));
                // Adding contact to list
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        // return eventList
        return eventList;
    }

    // code to update the single contact
    public int updateEvent(Evenement event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EVENT_NAME, event.getName());

        // updating row
        return db.update(TABLE_EVENTS, values,  EVENT_NAME + " = ?",
                new String[] { String.valueOf(event.getName()) });
    }

    // Deleting single contact
    public void deleteEvent(Evenement event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, EVENT_NAME + " = ?",
                new String[] { String.valueOf(event.getName()) });
        db.close();
    }

    // Getting contacts Count
    public int getEventCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}

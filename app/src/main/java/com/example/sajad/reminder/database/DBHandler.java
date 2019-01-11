package com.example.sajad.reminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sajad.reminder.model.ReminderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajad on 5/27/2018.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final String TAG = "sajad";
    private static final String DBNAME = "reminder";
    private static final int DBVERSION = 1;
    private static final String TABLE_NAME = "reminder";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";
    private static final String DATE = "date";
    private static final String REPEATE = "repeate";

    private static DBHandler sInstance;

    public static synchronized DBHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    public DBHandler(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT," +
                HOUR + " INTEGER," +
                MINUTE + " INTEGER," +
                DATE + " TEXT," +
                REPEATE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long addReminder(String title, int hour, int minute, String date, int repeate) {
        long id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TITLE, title);
            values.put(HOUR, hour);
            values.put(MINUTE, minute);
            values.put(DATE, date);
            values.put(REPEATE, repeate);

            id = db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add birth to database");
        } finally {
            db.endTransaction();
        }
        return id;
    }

    public void updateReminder(int id, String title, int hour, int minute, String date, int repeate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(HOUR, hour);
        values.put(MINUTE, minute);
        values.put(DATE, date);
        values.put(REPEATE, repeate);

        db.update(TABLE_NAME, values, "id=?", new String[]{id + ""});
    }

    public List<ReminderModel> getAllReminders() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        List<ReminderModel> reminders = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                int hour = cursor.getInt(cursor.getColumnIndexOrThrow(HOUR));
                int minute = cursor.getInt(cursor.getColumnIndexOrThrow(MINUTE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DATE));
                int repeate = cursor.getInt(cursor.getColumnIndexOrThrow(REPEATE));

                reminders.add(new ReminderModel(id, title, hour, minute, date, repeate));
            } while (cursor.moveToNext());
        }
        database.close();
        return reminders;
    }

    public int deleteReminder(int id) {
        int delete = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ReminderModel reminderModel = getReminder(id);
            delete = db.delete(TABLE_NAME, "id=?", new String[]{id + ""});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete birth");
        } finally {
            db.endTransaction();
        }
        return delete;
    }

    public ReminderModel getReminder(int id) {
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=" + id;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            ReminderModel reminder = new ReminderModel();
            reminder.setId(cursor.getInt(0));
            reminder.setTitle(cursor.getString(1));
            reminder.setHour(cursor.getInt(2));
            reminder.setMinute(cursor.getInt(3));
            reminder.setDate(cursor.getString(4));
            reminder.setRepeate(cursor.getInt(5));

            // Adding contact to list
            cursor.close();
            return reminder;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }
}

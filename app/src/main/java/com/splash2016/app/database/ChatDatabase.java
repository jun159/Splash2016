package com.splash2016.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.splash2016.app.objects.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAOJUN on 1/6/16.
 */
public class ChatDatabase extends SQLiteOpenHelper {

    private static final String TAG = ChatDatabase.class.getSimpleName();

    public static final String SUCCESS_DATABASE = "ChatDatabase is created";
    public static final String SUCCESS_TABLE = "ChatTable is created";
    public static final String SUCCESS_ROW_ADDED = "One chat row inserted";

    public static final String ID = "id";
    public static final String FRIEND_NAME = "friend_name";
    public static final String MESSAGE = "message";
    public static final String ISSELF = "is_self";
    public static final String DATE = "date";
    public static final String TIME = "time";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "chat_data";
    public static final String TABLE_NAME = "chat_table";

    public String query = "CREATE TABLE " + TABLE_NAME + " ("
                        + ID + " TEXT PRIMARY KEY, "
                        + FRIEND_NAME + " TEXT NOT NULL, "
                        + MESSAGE + " TEXT NOT NULL, "
                        + ISSELF + " TEXT NOT NULL, "
                        + DATE + " TEXT NOT NULL, "
                        + TIME + " TEXT NOT NULL); ";

    public ChatDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, SUCCESS_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
        Log.d(TAG, SUCCESS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void addMessage(ChatDatabase data, String friendName, String message,
                                  String isSelf, String date, String time) {
        SQLiteDatabase sqLiteDatabase = data.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID, generateID(data));
        contentValues.put(FRIEND_NAME, friendName);
        contentValues.put(MESSAGE, message);
        contentValues.put(ISSELF, isSelf);
        contentValues.put(DATE, date);
        contentValues.put(TIME, time);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, SUCCESS_ROW_ADDED);
    }

    public List<Message> getMessages(ChatDatabase data, String friendName) {
        List<Message> messageList = new ArrayList<>();
        Cursor cursor = getCursor(data);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Message message = new Message(cursor.getString(1), cursor.getString(2),
                    Boolean.parseBoolean(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5));

            if(message.getFriendName().equals(friendName)) {
                messageList.add(message);
            }

            cursor.moveToNext();
        }

        cursor.close();

        return messageList;
    }

    private String generateID(ChatDatabase data) {
        Cursor cursor = getCursor(data);
        cursor.moveToFirst();
        int size = 0;

        while(!cursor.isAfterLast()) {
            size++;
            cursor.moveToNext();
        }

        cursor.close();
        return String.valueOf(size);
    }

    private Cursor getCursor(ChatDatabase data) {
        // Read data from sqlite database
        SQLiteDatabase sqLiteDatabase = data.getReadableDatabase();
        String[] columns = { ID, FRIEND_NAME, MESSAGE, ISSELF, DATE, TIME };

        // Points to first row of table
        return sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
    }
}

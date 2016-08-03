package org.mat.eduvation.LocaL_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseConnector {
    private SQLiteDatabase db;
    private dbHelper helper;

    public DatabaseConnector(Context context) {
        helper = new dbHelper(context);
    }

    public void open() {
        db = helper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public Cursor getAllUsers() {
        String[] col = {dbHelper.COLUMN_ID, dbHelper.COLUMN_NAME, dbHelper.COLUMN_COMPANY,
                dbHelper.COLUMN_EMAIL, dbHelper.COLUMN_BIRTHDATE, dbHelper.COLUMN_FBKEY};

        return db.query(
                dbHelper.TABLE_NAME,
                col,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getUserByEmail(String email) {
        String[] columns = {dbHelper.COLUMN_NAME, dbHelper.COLUMN_COMPANY,
                dbHelper.COLUMN_EMAIL, dbHelper.COLUMN_BIRTHDATE, dbHelper.COLUMN_FBKEY};

        return db.query(
                dbHelper.TABLE_NAME, // table name
                columns, // column names
                dbHelper.COLUMN_EMAIL + " = '" + email + "'", // where clause // id param. could be here or appended as it is ^
                null, // where params
                null, // groupby
                null, // having
                null // orderby
        );
    }

    public void deleteUserById(int id) {
        open();
        db.delete(
                dbHelper.TABLE_NAME, // table name
                dbHelper.COLUMN_ID + "=" + id, // where clause
                null // where params
        );
        close();
    }


    public void insertUser(String Name, String Company, String Birthday, String Email, String FB_KEY) {
        ContentValues newItem = new ContentValues();
        newItem.put(dbHelper.COLUMN_NAME, Name);
        newItem.put(dbHelper.COLUMN_COMPANY, Company);
        newItem.put(dbHelper.COLUMN_EMAIL, Email);
        newItem.put(dbHelper.COLUMN_FBKEY, FB_KEY);
        newItem.put(dbHelper.COLUMN_BIRTHDATE, Birthday);

        db.insert(dbHelper.TABLE_NAME, null, newItem);

    }

    public boolean isExist(String email) {
        String[] columns = {dbHelper.COLUMN_NAME, dbHelper.COLUMN_COMPANY,
                dbHelper.COLUMN_EMAIL, dbHelper.COLUMN_BIRTHDATE, dbHelper.COLUMN_FBKEY};


        Cursor cursor = db.query(
                dbHelper.TABLE_NAME, // table name
                columns, // column names
                dbHelper.COLUMN_EMAIL + " = '" + email + "'", // where clause // id param. could be here or appended as it is ^
                null, // where params
                null, // groupby
                null, // having
                null // orderby
        );

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

    public boolean deleteAllUsers() {

        int doneDelete = 0;
        doneDelete = db.delete(dbHelper.TABLE_NAME, null, null);
        Log.w("num of rows deleted", Integer.toString(doneDelete));
        return doneDelete > 0;

    }


}



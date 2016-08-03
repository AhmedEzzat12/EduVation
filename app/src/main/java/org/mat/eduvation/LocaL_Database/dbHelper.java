package org.mat.eduvation.LocaL_Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class dbHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "USER";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_COMPANY = "COMPANY";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_BIRTHDATE = "BIRTHDATE";
    public static final String COLUMN_FBKEY = "FIREBASEKEY";

    private static final String DB_NAME = "eduvation.db";
    private static final int DB_VERSION = 1;
    private static final String DB_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_FBKEY + " TEXT, " +
                    COLUMN_COMPANY + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_BIRTHDATE + " TEXT);";

    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

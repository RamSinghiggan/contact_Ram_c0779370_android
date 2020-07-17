package com.example.contact_ram_c0779370_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Contact";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_ID = "id";
    private static  final String COLUMN_FIRST_NAME = "firstname";
    private static final String COLUMN_LAST_NAME = "lastname";
    private static final String COLUMN_EMAIL = "email";
    private static final String  COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE = "phone";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" +
                COLUMN_ID + " Integer not null constraint user_pk primary key autoincrement," +
                COLUMN_FIRST_NAME + " varchar(200) not null, " +
                COLUMN_LAST_NAME + " varchar(200) not null, " +
                COLUMN_EMAIL + " varchar(200) not null, "+
                COLUMN_ADDRESS + " varchar(200) not null, " +
                COLUMN_PHONE + " Integer not null);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    boolean addUser(String firstName, String lastName,String email, String address, String phone){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME,firstName);
        cv.put(COLUMN_LAST_NAME,lastName);
        cv.put(COLUMN_EMAIL,email);
        cv.put(COLUMN_ADDRESS,address);
        cv.put(COLUMN_PHONE,String.valueOf(phone));

        return  sqLiteDatabase.insert(TABLE_NAME,null, cv) != -1;
    }

    Cursor getAllUsers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return  sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);


    }

    boolean updateUser(int id,String firstname, String lastname,String email, String address, int phone) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, firstname);
        cv.put(COLUMN_LAST_NAME, lastname);
        cv.put(COLUMN_EMAIL,email);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_PHONE, phone);
        return  sqLiteDatabase.update(TABLE_NAME, cv,COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;

    }

    boolean deleteUser(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // the delete method  returns the number of rows affected
        return  sqLiteDatabase.delete(TABLE_NAME,COLUMN_ID + "=?" , new String[]{ String.valueOf(id)}) > 0;

    }
}

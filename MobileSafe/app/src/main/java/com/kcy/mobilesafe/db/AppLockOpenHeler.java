package com.kcy.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kcy on 2017/5/31.
 */

public class AppLockOpenHeler extends SQLiteOpenHelper{
    public AppLockOpenHeler(Context context) {
        super(context, "applock.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table applock (_id integer primary key autoincrement,packagename varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

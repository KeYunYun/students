package com.example.kcy.newstext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kcy on 2017/4/8.
 */

public class NewsOpenHepler extends SQLiteOpenHelper {
    public NewsOpenHepler(Context context) {
        super(context, "news", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table news (_id integer  ,title varchar(200),des varchar(300),icon_url varchar(200),news_url varchar(200)," +
                " type integer , time varchar(100),comment integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

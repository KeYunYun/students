package com.example.kcy.counterprovidertext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kcy on 2017/4/17.
 */

public class MyOpeanHelper extends SQLiteOpenHelper {
    public MyOpeanHelper(Context context) {

        super(context, "Account.db", null, 1);
        System.out.print("xxxxxxxxxxxxxxxxxxxxxxxx");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20),money varchar(20))");
        db.execSQL("insert into info(name,money) values(?,?)", new String[]{"张三","5000"});
        db.execSQL("insert into info(name,money) values(?,?)", new String[]{"李四","3000"});
        System.out.print("yyyyyyyyyyyyyyyyyyyyyyyy");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

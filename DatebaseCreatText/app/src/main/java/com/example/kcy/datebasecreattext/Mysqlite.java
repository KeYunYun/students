package com.example.kcy.datebasecreattext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kcy on 2017/4/4.
 */

public class Mysqlite extends SQLiteOpenHelper {

    public Mysqlite(Context context) {
        super(context, "info.db", null,2);
    }
    //创建数据库

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table info (_id integer primary key autoincrement,name varchar(20))");
        //创建表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("alter table info add phone varchar(11)");
       //版本号改变后修改表
    }
}

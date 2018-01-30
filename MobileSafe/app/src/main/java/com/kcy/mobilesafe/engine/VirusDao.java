package com.kcy.mobilesafe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcy on 2017/5/23.
 */

public class VirusDao {
    public  static String path="/data/data/com.kcy.mobilesafe/databases/antivirus.db";
    //开启数据库，查询数据库中对应的MD5码
    public static List<String> getVirusList(){
        SQLiteDatabase db =SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        Cursor cursor =db.query("datable",new String[]{"md5"},null,null,null,null,null);
         List<String> virusList =new ArrayList<String>();

        while (cursor.moveToNext()){
           virusList.add(  cursor.getString(0));
        }
        cursor.close();
        db.close();
        return virusList;
    }

}

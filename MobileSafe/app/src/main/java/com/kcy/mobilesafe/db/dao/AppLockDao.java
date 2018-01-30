package com.kcy.mobilesafe.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcy.mobilesafe.db.AppLockOpenHeler;
import com.kcy.mobilesafe.db.BlackNumberOpenHeler;
import com.kcy.mobilesafe.db.domain.BlackNumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcy on 2017/5/31.
 */

public class AppLockDao {
    private AppLockOpenHeler appLockOpenHeler;
    //单例模式
    //1.私有化构造方法
    private AppLockDao(Context context){

        appLockOpenHeler=new AppLockOpenHeler(context);
    }
    //2.声明一个当前类对象
    private static AppLockDao appLockDao=null;
    //3.提供一个方法
    public static AppLockDao getInstance(Context context){
        if(appLockDao==null){
            appLockDao =new AppLockDao(context);
        }
        return appLockDao;
    }

    public void insert(String packname){
        SQLiteDatabase db=appLockOpenHeler.getWritableDatabase();
        //获得键值对
        ContentValues values=new ContentValues();
        values.put("packagename",packname);
        db.insert("applock",null,values);
        db.close();
    }
    public void delete(String packagename){
        //获得数据库
        SQLiteDatabase db =appLockOpenHeler.getReadableDatabase();
        db.delete("applock","packagename=?",new String[]{packagename});
        db.close();
    }
    public List<String> findAll(){
        SQLiteDatabase db =appLockOpenHeler.getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from applock",null);
        List<String> list = new ArrayList<String>();
        while (cursor.moveToNext()){
          String packagename= cursor.getString(1);
            list.add(packagename);
        }
        cursor.close();
        db.close();

        return list;
    }



}

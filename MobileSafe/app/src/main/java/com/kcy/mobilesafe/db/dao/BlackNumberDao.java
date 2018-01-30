package com.kcy.mobilesafe.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kcy.mobilesafe.db.BlackNumberOpenHeler;
import com.kcy.mobilesafe.db.domain.BlackNumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcy on 2017/5/31.
 */

public class BlackNumberDao  {
    private BlackNumberOpenHeler blackNumberOpenHeler;
    //单例模式
    //1.私有化构造方法
    private BlackNumberDao(Context context){
        blackNumberOpenHeler=new BlackNumberOpenHeler(context);
    }
    //2.声明一个当前类对象
    private static BlackNumberDao blackNumberDao=null;
    //3.提供一个方法
    public static BlackNumberDao getInstance(Context context){
        if(blackNumberDao==null){
            blackNumberDao =new BlackNumberDao(context);
        }
        return blackNumberDao;
    }

    public void insert(String phone ,String mode ){
        SQLiteDatabase db=blackNumberOpenHeler.getWritableDatabase();
        //获得键值对
        ContentValues values=new ContentValues();
        values.put("phone",phone);
        values.put("mode",mode);
        db.insert("blacknumber",null,values);
        db.close();
    }
    public void delete(String phone){
        //获得数据库
        SQLiteDatabase db =blackNumberOpenHeler.getReadableDatabase();
        db.delete("blacknumber","phone=?",new String[]{phone});
        db.close();
    }
    public void update(String phone ,String mode){
        SQLiteDatabase db =blackNumberOpenHeler.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("mode",mode);
        db.update("blacknumber",values,"phone=?",new String[]{phone});
        db.close();
    }
    public List<BlackNumberInfo> findAll(){
        SQLiteDatabase db =blackNumberOpenHeler.getReadableDatabase();
        Cursor cursor= db.query("blacknumber",new String[]{"phone","mode"},null,null,null,null,"_id desc");
        List<BlackNumberInfo> list = new ArrayList<BlackNumberInfo>();
        while (cursor.moveToNext()){
            BlackNumberInfo blackNumberInfo=new BlackNumberInfo();
           blackNumberInfo.phone= cursor.getString(0);
           blackNumberInfo.mode= cursor.getString(1);
            list.add(blackNumberInfo);
        }
        cursor.close();
        db.close();

        return list;
    }

    public  List<BlackNumberInfo> find(int index){
        SQLiteDatabase db =blackNumberOpenHeler.getReadableDatabase();
        Cursor cursor= db.rawQuery("select phone,mode from blacknumber order by _id desc limit ? ,20;", new String[]{index+""});
        List<BlackNumberInfo> list = new ArrayList<BlackNumberInfo>();
        while (cursor.moveToNext()){
            BlackNumberInfo blackNumberInfo=new BlackNumberInfo();
            blackNumberInfo.phone= cursor.getString(0);
            blackNumberInfo.mode= cursor.getString(1);
            list.add(blackNumberInfo);
        }
        cursor.close();
        db.close();

        return list;
    }
    public int getCount(){
        SQLiteDatabase db =blackNumberOpenHeler.getReadableDatabase();
        int count=0;
        Cursor cursor=db.rawQuery("select count(*) from blacknumber;",null);
        if(cursor.moveToNext()){
            count=cursor.getInt(0);
        }
        return count;
    }
    //电话号码对应的模式
    public int getMode(String phone){
        SQLiteDatabase db =blackNumberOpenHeler.getReadableDatabase();
        int mode=0;
        Cursor cursor=db.query("blacknumber",new String[]{"mode"},"phone=?",new String[]{phone},null,null,null);
        if(cursor.moveToNext()){
            mode=cursor.getInt(0);
        }
        return mode;
    }

}

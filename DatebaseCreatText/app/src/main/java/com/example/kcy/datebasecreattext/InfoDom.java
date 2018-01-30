package com.example.kcy.datebasecreattext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by kcy on 2017/4/5.
 */

public class InfoDom {
    Mysqlite mysqlite;
    public InfoDom(Context context){
       mysqlite=new Mysqlite(context);

    }

    public boolean add(InfoBean bean){
        SQLiteDatabase db=mysqlite.getReadableDatabase();
      //  db.execSQL("insert into info(name,phone) values(?,?);", new Object[]{bean.name,bean.phone});
        ContentValues contentvaluse=new ContentValues();//用于存值
        contentvaluse.put("name",bean.name);
        contentvaluse.put("phone",bean.phone);
       long inset= db.insert("info",null,contentvaluse);
        // 返回值表示添加新的这一行的ID ，值为-1表示添加失败
        //table:表名  nullcolumHack :   valuse:存放添加数据一行的值是一个hasMap
        db.close();
        if(inset==-1){
            return false;
        }else {
            return true;
        }
    }
    public int del(String name){
        SQLiteDatabase db=mysqlite.getReadableDatabase();
      //  db.execSQL("delete from info where name=?;", new Object[]{name});
        int insert =  db.delete("info","name=?",new String[]{name});
        //放回值表示成功删除了多少行
        //table:表名  whereClaus：删除表的条件 whereArg:  条件的占位符
        db.close();
        return insert;
    }
    public int update(InfoBean bean){
        SQLiteDatabase db=mysqlite.getReadableDatabase();
       // db.execSQL("update info set phone=? where name=?;", new Object[]{bean.phone,bean.name});
       ContentValues values=new ContentValues();
        values.put("phone",bean.phone);
        int insert=db.update("info",values,"name=?",new String[]{bean.name});
        //  表名,修改的表名在ContentValues中定义和赋值，条件，条件的占位符
        //返回修改成功的行数
        db.close();
        return insert;
    }
    public ArrayList<InfoBean>  select(String name){
        ArrayList<InfoBean> list =new ArrayList<InfoBean>();
        SQLiteDatabase db=mysqlite.getReadableDatabase();
        Cursor cursor=db.rawQuery("select _id, name,phone from info where name = ?;", new String []{name});
        if(cursor!=null||cursor.getCount()>0){
            while (cursor.moveToNext()){
                InfoBean bean= new InfoBean();

                bean.id =  cursor.getInt(0)+"";
               bean.name=cursor.getString(1);
                bean.phone=cursor.getString(2);
                list.add(bean);
            }
        }
        return list;
    }
}

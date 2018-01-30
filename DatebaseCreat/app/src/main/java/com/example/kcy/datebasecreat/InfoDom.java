package com.example.kcy.datebasecreat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kcy on 2017/4/3.
 */

public class InfoDom {
    //执行SQL语句需要SqliteDatabase对象
    SqlliteOpean sqlliteOpean;
    public InfoDom(Context context){
        sqlliteOpean =new SqlliteOpean(context);

    }
    public void add(InfoBean bean){
        SQLiteDatabase db = sqlliteOpean.getReadableDatabase();
        //占位符
        db.execSQL("insert into info(name,phone) values(?,?);",new Object[]{bean.name,bean.phone});
        db.close();
    }
    public void del(String name){
        SQLiteDatabase db = sqlliteOpean.getReadableDatabase();
        //占位符
        db.execSQL("delete from info where name=?;",new Object[]{name});
        db.close();
    }
    public void update(InfoBean bean ){

        SQLiteDatabase db = sqlliteOpean.getReadableDatabase();
        //占位符
        db.execSQL(" update info set phone=? where name=?;",new Object[]{bean.phone,bean.name});
        db.close();

    }
    public void select(String name){
        SQLiteDatabase db = sqlliteOpean.getReadableDatabase();
        //查询返回一个cursor结果集，selectionArgs代表查询条件占位符的值
        Cursor cursor= db.rawQuery("select _id name,phone from info where name=? ",new String[]{name});
        //解析获得cursor中的数据
        //getCount()获得结果集里面的行数
        if(cursor !=null &&cursor.getCount() >0){
            //循环遍历结果集
            while (cursor.moveToNext()){
                int id= cursor.getInt(0);
                String name_str=cursor.getString(1);
                String phone_str=cursor.getString(2);
                System.out.println(id +name_str+phone_str);
            }
            cursor.close();
        }
        db.close();
    }
}

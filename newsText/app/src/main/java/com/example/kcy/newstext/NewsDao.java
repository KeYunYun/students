package com.example.kcy.newstext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by kcy on 2017/4/8.
 */


public class NewsDao {
    private NewsOpenHepler newsOpenHepler;
    public NewsDao(Context context){
        newsOpenHepler=new NewsOpenHepler(context);
    }
    public ArrayList<NewBean> getNews(){
        ArrayList<NewBean> list= new ArrayList<NewBean>();
        SQLiteDatabase db=newsOpenHepler.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from news",null);
        if(cursor!=null||cursor.getCount()>0){
            while(cursor.moveToNext()){
                NewBean newsBean = new NewBean();
                newsBean. id = cursor.getInt(0);
                newsBean. title = cursor.getString(1);
                newsBean. des =	cursor.getString(2);
                newsBean. icon_url =	cursor.getString(3);
                newsBean. new_url =	cursor.getString(4);
                newsBean. 	type = cursor.getInt(5);
                newsBean. time =	cursor.getString(6);
                newsBean. 	comment = cursor.getInt(7);

                list.add(newsBean);
            }

        }
        db.close();
        cursor.close();


        db.close();
        cursor.close();


        return list;

    }

    public void saveNews(ArrayList<NewBean> list){
        SQLiteDatabase db =newsOpenHepler.getReadableDatabase();

        for(NewBean newBean :list){
            ContentValues values =new ContentValues();
            values.put("_id",newBean.id);
            values.put("title", newBean.title);
            values.put("des", newBean.des);
            values.put("icon_url", newBean.icon_url);
            values.put("news_url", newBean.new_url);
            values.put("type", newBean.type);
            values.put("time", newBean.time);
            values.put("comment", newBean.comment);
            db.insert("news", null, values);
        }
        db.close();
    }

    //删除数据库中缓存的旧数据
    public void delete(){

        //通过帮助类对象获取一个数据库操作对象
        SQLiteDatabase db = newsOpenHepler.getReadableDatabase();
        db.delete("news", null, null);
        db.close();
    }
}

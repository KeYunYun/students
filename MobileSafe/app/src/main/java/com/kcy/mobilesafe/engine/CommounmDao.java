package com.kcy.mobilesafe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcy on 2017/5/23.
 */

public class CommounmDao {
    public  static String path="/data/data/com.kcy.mobilesafe/databases/commonnum.db";
    //获取组
    public List<Grop> getGrou(){
        SQLiteDatabase db=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
       Cursor cursor=  db.query("classlist",new String[]{"name","idx"},null,null,null,null,null);
        List<Grop> grouList= new ArrayList<Grop>();
        while (cursor.moveToNext()){
            Grop grop=new Grop();
           grop.name= cursor.getString(0);
            grop.idx=cursor.getString(1);
            grop.childList=getChild(grop.idx);
            grouList.add(grop);
        }
        db.close();
        cursor.close();
        return grouList;
   }
   //获得每个组的子节点
    public  List<Child> getChild(String idx){
        SQLiteDatabase db=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
        Cursor cursor=  db.rawQuery("select * from table"+idx+";",null);
        List<Child> childList=new ArrayList<Child>();
        while (cursor.moveToNext()){
          Child child=new Child();
            child._id= cursor.getString(0);
           child.number=cursor.getString(1);
            child.name=cursor.getString(2);
            childList.add(child);
        }
        cursor.close();
        db.close();
        return childList;

    }
   public class Grop{
       public String name;
       public String idx;
       public List<Child> childList;
   }
   public class  Child{
       public String _id;
       public String number;
       public String name;
   }
}

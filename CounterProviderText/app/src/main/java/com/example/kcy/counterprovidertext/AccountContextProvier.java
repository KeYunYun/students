package com.example.kcy.counterprovidertext;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by kcy on 2017/4/17.
 */

public class AccountContextProvier extends ContentProvider{
    //定义一个uri地址选择器
    private static final int QUERYSUCESS=0;
    private static final int INSERTSUCESS=1;
    private static final int UPDATESUCESS=2;
    private static final int DELETESUCESS=3;

    MyOpeanHelper myopeanhelper;
    private static final UriMatcher sURIMAther = new UriMatcher(UriMatcher.NO_MATCH);
    //创建一个静态快，在里面添加URiMather
    static {
        //和清单文件定义的一样：前两个参数决定了URI的地址 com.kcy.Account/query:
        //表示用该URL访问query方法
        //当uri被匹配后返回code
        sURIMAther.addURI("com.kcy.Account","query",QUERYSUCESS);
        sURIMAther.addURI("com.kcy.Account","insert",INSERTSUCESS);
        sURIMAther.addURI("com.kcy.Account","delete",DELETESUCESS);
        sURIMAther.addURI("com.kcy.Account","update",UPDATESUCESS);
        //可以添加多个url
    }
    //当内容提供者初始化 会执行该方法
    @Override
    public boolean onCreate() {
        //初始化openHandler对象
       myopeanhelper = new MyOpeanHelper(getContext());
        System.out.print("ccccccccccccccccccccccccccccc");
        return false;
    }
    //对外暴露
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        System.out.print("xyyyyyyyyyyyyxyxyxy");
        int code= sURIMAther.match(uri);
        if(code==QUERYSUCESS){
        SQLiteDatabase db =myopeanhelper.getReadableDatabase();
         Cursor cursor =   db.query("info",projection,selection,selectionArgs,null,null,sortOrder);
            System.out.println("uri路径匹配chengg");
          //  db.close();
        return cursor;
        }else{

            System.out.println("uri路径不匹配");
            return null;
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code =sURIMAther.match(uri);
        if(code==INSERTSUCESS){

            SQLiteDatabase db=myopeanhelper.getReadableDatabase();
            long insrt=db.insert("info",null,values);
            Uri ur=Uri.parse("com.kcy.insert/"+insrt);
            System.out.println(ur);
            //db.close();
            return  ur;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
       int code =sURIMAther.match(uri);
        if(code==DELETESUCESS){
            SQLiteDatabase db=myopeanhelper.getReadableDatabase();
           int delte=  db.delete("info",selection,selectionArgs);
            return delte;
        }

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
       int code= sURIMAther.match(uri);
        if(code==UPDATESUCESS){
            SQLiteDatabase db =myopeanhelper.getReadableDatabase();
            int updateint= db.update("info",values,selection,selectionArgs);
            return updateint;
        }
        return 0;
    }
}

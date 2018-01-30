package com.example.kcy.counterprovidertext;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyOpeanHelper helper = new MyOpeanHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        //需求把张三和李四的数据取出来
        Cursor cursor = db.query("info", null, null, null, null, null, null);
        if (cursor!=null&&cursor.getCount()>0) {
            while(cursor.moveToNext()){

                String name = cursor.getString(1);
                String money = cursor.getString(2);

                System.out.println("name:"+name+"-----"+money);

            }
            cursor.close();

        }


    }
}

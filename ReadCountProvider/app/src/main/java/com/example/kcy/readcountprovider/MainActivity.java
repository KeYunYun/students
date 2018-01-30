package com.example.kcy.readcountprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt_add;
    Button bt_update;
    Button bt_delte;
    Button bt_query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_add= (Button) findViewById(R.id.bt_add);
        bt_update= (Button) findViewById(R.id.bt_update);
        bt_delte= (Button) findViewById(R.id.bt_delete);
        bt_query= (Button) findViewById(R.id.bt_query);
        bt_add.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        bt_delte.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_add:
                onclikAdd();
                break;
            case R.id.bt_update:
                onclikUpdate();
                break;
            case R.id.bt_delete:
                onclikDelete();
                break;
            case R.id.bt_query:
                onclikQuery();
                break;
            default:break;

        }
    }

    private void onclikQuery() {
        Uri uri = Uri.parse("content://com.kcy.Account/query");
        //获取内容解析者获取数据
        Cursor cursor = getContentResolver().query(uri, new String[]{"name","money"}, null, null, null);
        if(cursor!=null){
            System.out.println(cursor.getCount());
            while (cursor.moveToNext()&&cursor.getCount()>0){

                String name = cursor.getString(0);
                System.out.println(name);
                String money = cursor.getString(1);
                System.out.println(money);
               Toast.makeText(getApplicationContext(),name+money,Toast.LENGTH_SHORT).show();
                System.out.println(name+money);
            }
        }
    }

    private void onclikDelete() {
        Uri uri =Uri.parse("content://com.kcy.Account/delete");
        int d= getContentResolver().delete(uri,"name=?",new String[]{"张里"});
        Toast.makeText(getApplicationContext(),"根新了"+d+"行",Toast.LENGTH_SHORT).show();
    }

    private void onclikUpdate() {
        Uri uri =Uri.parse("content://com.kcy.Account/update");
        ContentValues valus = new ContentValues();
        valus.put("money",2222);
        getContentResolver().update(uri,valus,"name=?",new String[]{"张里"});
        Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
    }

    private void onclikAdd() {
        Uri uri =Uri.parse("content://com.kcy.Account/insert");
        ContentValues values=new ContentValues();
        values.put("name","张里");
        values.put("money",1999);
        Uri uri2= getContentResolver().insert(uri,values);
        Toast.makeText(getApplicationContext(),""+uri2,Toast.LENGTH_SHORT).show();
    }
}

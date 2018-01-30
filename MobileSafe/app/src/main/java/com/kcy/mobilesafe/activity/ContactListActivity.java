package com.kcy.mobilesafe.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kcy.mobilesafe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kcy on 2017/5/21.
 */

public class ContactListActivity extends Activity{
    ListView lv_contact;
    private MyAdapter myadapter;
    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myadapter=  new MyAdapter();
            lv_contact.setAdapter(myadapter);
        }
    };
    private List<HashMap<String,String>> contactlist=null;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);
        initUI();
        initDate();
    }

    private void initDate() {
        //通过内容解析器来解析系统联系人
        //1.内容通过者
        //2.Url地址，查看系统联系人数据库
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ContactListActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
                } else {
                    ContentResolver contentresovler = getContentResolver();
                    Cursor cursor = contentresovler.query(Uri.parse("content://com.android.contacts/raw_contacts"),
                            new String[]{"contact_id"},
                            null,
                            null,
                            null);
                    //3.循环游标只到没有数据为止
                    contactlist=new ArrayList<HashMap<String, String>>();
                    contactlist.clear();
                    while (cursor.moveToNext()) {
                        String id = cursor.getString(0);
                        Log.i("id", id);
                       Cursor indexCuror= contentresovler.query(Uri.parse("content://com.android.contacts/data"),
                                new String[]{"data1","mimetype"},
                               "raw_contact_id=?",new String[]{id},
                                null);
                        HashMap<String ,String> hasMap=new HashMap<String, String>();

                        while (indexCuror.moveToNext()){
                            String date=indexCuror.getString(0);
                           String type= indexCuror.getString(1);
                            Log.i("data1", "run: " +indexCuror.getString(0));
                            Log.i("data2", "run: " +indexCuror.getString(1));
                            if(type.equals("vnd.android.cursor.item/phone_v2")){
                                if(!TextUtils.isEmpty(date)){
                                    hasMap.put("phonenum",date);
                                }
                            }else{
                                hasMap.put("name",date);
                            }
                        }
                        contactlist.add(hasMap);
                        indexCuror.close();
                    }
                    cursor.close();
                    mHandler.sendEmptyMessage(0);
                }
            };
            }.start();

    }

    private void initUI() {
        lv_contact= (ListView) findViewById(R.id.lv_contact);
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(myadapter!=null){
                 HashMap<String ,String > hasmap=   myadapter.getItem(position);
                String phone=hasmap.get("phonenum");
                    Intent intent=new Intent();
                    intent.putExtra("phone",phone);
                    setResult(0,intent);
                    Log.i("phone1", "onActivityResult: "+phone);
                    finish();
                }

            }
        });
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return contactlist.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return contactlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View view= View.inflate(getApplicationContext(),R.layout.listview_contact_item,null);
            TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone= (TextView) view.findViewById(R.id.tv_phonenum);
            tv_name.setText(getItem(position).get("name"));
            tv_phone.setText(getItem(position).get("phonenum"));
            return view;
        }
    }
}

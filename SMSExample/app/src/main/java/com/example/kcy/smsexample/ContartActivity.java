package com.example.kcy.smsexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcy on 2017/4/12.
 */

public class ContartActivity  extends Activity{
    ListView lv_look;
    List<Contact> contactList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contact);
        lv_look= (ListView) findViewById(R.id.lv_look);
         contactList=new ArrayList<Contact>();
        for(int i=0 ;i<10;i++){
            Contact contact=new Contact();
            contact.setName("张"+i+"三");
            contact.setPhone("1234567"+i+i);
            contactList.add(contact);
        }
        //

        lv_look.setAdapter(new MyAdapter());
        lv_look.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //取出电点动条目的数据
                String phone=contactList.get(position).getPhone();
                Intent intent=new Intent();
                intent.putExtra("phone",phone);
                setResult(10,intent);
                //关闭当前Activity   就会执行onActivityResult
                finish();
            }
        });

    }

    //创建listView适配器
    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return contactList.size() ;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View view;
            if(null==convertView){
                view =View.inflate(getApplicationContext(),R.layout.layout_content,null);
            }else{
                view=convertView;
            }
            TextView tv_name= (TextView) findViewById(R.id.tv_name);
            TextView tv_phone=(TextView) findViewById(R.id.tv_phone);

            System.out.println(contactList.get(position).getPhone());
            System.out.println(findViewById(R.id.tv_name));

          //  tv_name.setText(contactList.get(position).getName());
           // tv_phone.setText(contactList.get(position).getPhone());
            return view;
        }
    }
}

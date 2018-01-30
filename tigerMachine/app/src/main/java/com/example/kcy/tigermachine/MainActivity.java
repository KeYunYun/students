package com.example.kcy.tigermachine;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    Context mcontext;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext=this;
        ListView lv_tier1= (ListView) findViewById(R.id.lv_tiger1);
        ListView lv_tier2= (ListView) findViewById(R.id.lv_tiger2);
        ListView lv_tier3= (ListView) findViewById(R.id.lv_tiger3);
        ListAdapter listAdapter=new Listadper();
        lv_tier1.setAdapter(listAdapter);
        lv_tier2.setAdapter(listAdapter);
        lv_tier3.setAdapter(listAdapter);

    }
    class Listadper extends BaseAdapter{


        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view =null;
            if (convertView!=null){
                view= (TextView) convertView;
            }else{
                view=new TextView(mcontext);
            }
            view.setTextSize(30);
            Random random =new Random();
            int  num= random.nextInt(100);
            if(num<20){
                view.setTextColor(Color.BLUE);
                view.setText("桃");
            }else if(num<40){
                view.setTextColor(Color.RED);
                view.setText("杏");
            }else if (num<60){
                view.setTextColor(Color.GREEN);
                view.setText("梨");
            }else if(num<80){
                view.setTextColor(Color.GRAY);
                view.setText("瓜");
            }else{
                view.setTextColor(Color.YELLOW);
                view.setText("枣");
            }
            return view;
        }

    }
}

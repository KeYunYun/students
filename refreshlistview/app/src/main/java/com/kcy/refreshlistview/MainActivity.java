package com.kcy.refreshlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RefreshListView listview;
    private ArrayList<String> listdatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (RefreshListView) findViewById(R.id.listview);
          listdatas= new ArrayList<String>();
        for(int i=0;i<30;i++){
            listdatas.add("这是第"+i+"条数据");
        }

        listview.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return listdatas.size();
        }

        @Override
        public Object getItem(int position) {
            return listdatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;

                TextView textview =new TextView(parent.getContext());
                view=textview;

            textview.setText(listdatas.get(position));
            Log.i("getView", "getView: "+listdatas.get(position));
            textview.setTextSize(20);
            return view;
        }
    }
}

package com.kcy.dropdownlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private EditText et_input;
    private ArrayList<String> datas;
    private   PopupWindow popuwindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_input= (EditText) findViewById(R.id.et_input);
        findViewById(R.id.ib_dropdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopuoWindow();
            }
        });
    }

    private void showPopuoWindow() {
        initListView();

        popuwindow = new PopupWindow(listView,et_input.getWidth(),900);
        //显示在指定控件下面
       popuwindow.setFocusable(true);
        //设置获得焦点
        popuwindow.showAsDropDown(et_input,0,0);
    }

    private void initListView() {
        listView=new ListView(this);
        listView.setDividerHeight(0);
        listView.setBackgroundResource(R.drawable.listview_background);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_input.setText(datas.get(position));
                popuwindow.dismiss();
            }
        });
        datas=new ArrayList<String>();
        for(int i=0;i<30;i++){
            datas.add(10000+i+" ");
        }
        listView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if(convertView==null){
                convertView=View.inflate(getApplicationContext(),R.layout.item_number_list,null);
            }
            TextView tv_number= (TextView) convertView.findViewById(R.id.tv_number);
            tv_number.setText(datas.get(position));
           convertView.findViewById(R.id.ib_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datas.remove(position);
                    notifyDataSetChanged();
                    if(datas.size()==0){
                        popuwindow.dismiss();
                    }
                }
            });
            return convertView;
        }
    }
}

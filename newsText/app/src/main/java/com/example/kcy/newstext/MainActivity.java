package com.example.kcy.newstext;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
   private ListView lv_view;
    private Context mcontext;

    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ArrayList<NewBean> newbean  = (ArrayList<NewBean>) msg.obj;

            NewsAdapter newsAdapter=new NewsAdapter(newbean,mcontext);
            lv_view.setAdapter(newsAdapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext=this;
        lv_view= (ListView) findViewById(R.id.lv_news);


        ArrayList<NewBean> allnews=NesUtils.getAllNewsDB(mcontext);
        if (allnews !=null && allnews.size()>0){

            NewsAdapter newsAdapter=new NewsAdapter(allnews,mcontext);
            lv_view.setAdapter(newsAdapter);
        }




        //设置listView条目的点击事件
        lv_view.setOnItemClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<NewBean> allnews=NesUtils.getAllNewsWeb(mcontext);

                Message msg= Message.obtain();
                msg.obj=allnews;
                handler.sendMessage(msg);
            }
        }).start();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //parent代表ListView
        //view: 条目上的view对象
        //position:条目的位置
        //id： 条目的id
        NewBean objects = (NewBean) parent.getItemAtPosition(position);
        String url= objects.new_url;
        Intent intent =new Intent();
        intent.setAction(intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}

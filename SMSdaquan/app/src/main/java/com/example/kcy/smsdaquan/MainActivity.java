package com.example.kcy.smsdaquan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    String[] sms_mess={"好运为你铺天盖地，健康陪你花天酒地，成功伴你惊天动地，平安随你上天入地，幸福替你开天辟地，祝福愿你欢天喜地。",
    "感动你，是我最向往的事；快乐你，是我最高兴的事；祝福你，是我正在做的事：祝福人生旅途中万事都如意，开心有情趣，神采总奕奕",
    "日给你温暖，月给你温馨，我给你祝福，祝你快乐密集像雨点，烦恼飞去像流云，忧愁灭绝像恐龙，幸福甜蜜像蜂蜜。祝你天天好心情！",
    "翩翩时光，悠悠岁月，宁静而温暖。流淌的轻风，斑驳的流年，把快乐好运带到你身边。思念弥漫，祝福飘散，愿你悠闲徜徉幸福的河岸"};
    ListView lv_look;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_look= (ListView) findViewById(R.id.lv_look);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.layout_item,sms_mess);
        lv_look.setAdapter(adapter);
        //设置条目的监听
        lv_look.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //条目被点击的时候调用
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //parent : 代表listView  view:代表下一级：textview
            //数据在哪里存着就在哪里取
               String content=  sms_mess[position];
                System.out.println(content);
                //跳转发送短信的界面  用隐私意图
                Intent intent =new Intent();
                intent.putExtra("sms_body",content);
                intent.setAction("android.intent.action.SEND");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setType("text/plain");
                startActivity(intent);

            }
        });
    }
}

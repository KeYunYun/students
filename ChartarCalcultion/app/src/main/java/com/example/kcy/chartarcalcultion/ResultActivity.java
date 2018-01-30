package com.example.kcy.chartarcalcultion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

/**
 * Created by kcy on 2017/4/11.
 */

public class ResultActivity extends Activity {
   TextView tv_name;
    TextView tv_gener;
    TextView tv_resut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_result);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_gener= (TextView) findViewById(R.id.tv_gener);
        tv_resut= (TextView) findViewById(R.id.tv_result);
        Intent intent=getIntent();
        //获取开启当前页面的intent
        //uri:统一资源标识符
        //获得数据
        String name =intent.getStringExtra("name");
        int sex=intent.getIntExtra("sex",-1);
        System.out.println(name+"--------"+sex);

        //显示数据到控件
        tv_name.setText(name);
        switch (sex){
            case 1:
                tv_gener.setText("男");
                try {
                    name.getBytes("GBK");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                tv_gener.setText("女");
                try {
                    name.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                tv_gener.setText("人妖");
                try {
                    name.getBytes("iso-8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            default:break;
        }
        //根据姓名和性别显示得分
        byte[] bytes =name.getBytes();
        int total=0;
        for(byte b:bytes){
          int num=   b&0xff;
            total+=num;
        }
        int score=Math.abs(total)%100;
        System.out.println(score);
        if(score>90){
            tv_resut.setText("人品很好");
        }
        else if(score>80){
            tv_resut.setText("人品还可以");
        }        else if(score>70){
            tv_resut.setText("70");
        }        else if(score>60){
            tv_resut.setText("60");
        }        else{
            tv_resut.setText("人品不及格");
        }
    }
}

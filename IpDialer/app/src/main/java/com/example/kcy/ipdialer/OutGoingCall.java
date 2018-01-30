package com.example.kcy.ipdialer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by kcy on 2017/4/12.
 */

public class OutGoingCall extends BroadcastReceiver {
   //当接收到外拨电话是执行该方法
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        Toast.makeText(context, data + "/cast", Toast.LENGTH_LONG).show();
        String phonename=getResultData();

        SharedPreferences sp=context.getSharedPreferences("config",0);
        String num=sp.getString("num","");

        System.out.println("你好 ++++++++++++++++++++++ "+num);
        System.out.println("ni hao ++++++++++++++++++++++ "+phonename);

        setResultData("17951"+phonename);
    }
}

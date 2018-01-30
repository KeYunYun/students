package com.example.kcy.revier_boradtext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by kcy on 2017/4/13.
 */

public class ReceiverCustomReceiver  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String content=intent.getStringExtra("name");
        System.out.println(content);
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}

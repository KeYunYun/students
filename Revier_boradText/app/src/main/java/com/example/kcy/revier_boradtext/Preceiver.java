package com.example.kcy.revier_boradtext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by kcy on 2017/4/13.
 */

public class Preceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String content=getResultData();
        System.out.println("pppp"+content);
        Toast.makeText(context,"pppp"+content,Toast.LENGTH_SHORT).show();
        setResultData("发送了pppp的信息");
        System.out.println(content);

    }
}

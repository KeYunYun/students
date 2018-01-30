package com.example.kcy.revier_boradtext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kcy on 2017/4/13.
 */

public class ScreenRecesiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //获得广播的事件类型
        String action =intent.getAction();
        if("android.intent.action.SCREEN_OFF".equals(action)){
            System.out.println("锁屏了");
        }else if("android.intent.action.SCREEN_ON".equals(action)){
            System.out.println("开屏了");
        }
    }
}

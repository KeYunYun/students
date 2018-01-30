package com.example.kcy.smsmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by kcy on 2017/4/13.
 */

public class SmsLiterRecesiver  extends BroadcastReceiver {
    String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED" ;
    @Override
    public void onReceive(Context context, Intent intent) {



            if (intent.getAction().equals( SMS_RECEIVED )) {
                // 相关处理 : 地域变换、电量不足、来电来信；
                System.out.println("++++++++++++++++++++++++++++++++++++");
                Toast.makeText(context,"+++++++++++++++",Toast.LENGTH_SHORT).show();

            }




    }
}

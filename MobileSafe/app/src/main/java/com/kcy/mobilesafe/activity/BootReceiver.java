package com.kcy.mobilesafe.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;

/**
 * Created by kcy on 2017/5/22.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("boot","开机监听");
       String oldSerierNum= Sputils.getString(context, ConstantValue.SIM_NUMBER,"");
       TelephonyManager manage= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String newSerierNum=manage.getSimSerialNumber();
        if(!oldSerierNum.equals(newSerierNum)){

                SmsManager smsManager=SmsManager.getDefault();
                String phone=Sputils.getString(context,ConstantValue.CONTACT_PHONE,"");
                smsManager.sendTextMessage(phone,null,"sim change",null,null);


        }else {

        }
    }
}

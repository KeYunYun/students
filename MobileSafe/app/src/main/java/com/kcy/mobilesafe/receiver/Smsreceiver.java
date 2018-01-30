package com.kcy.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.server.LocationService;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;

import java.util.Objects;

/**
 * Created by kcy on 2017/5/22.
 */

public class Smsreceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       boolean open_security=Sputils.getBoolean(context, ConstantValue.OPEN_SECURITY,false);
        Log.i("SMS1", "onReceive: "+open_security);
        if(open_security){
            //获得短信内容
            Object[] objectses= (Object[]) intent.getExtras().get("pdus");
            for(Object obj: objectses){

                SmsMessage smsmage=SmsMessage.createFromPdu((byte[])obj );
                //获得短信的基本信息
                String originatingAddress=smsmage.getOriginatingAddress();
                String messageBody= smsmage.getMessageBody();
                Log.i("SMS1", "onReceive: "+originatingAddress);
                Log.i("SMS2", "onReceive: "+messageBody);
                if(messageBody.contains("#*alarm*#")){
                   MediaPlayer player=  MediaPlayer.create(context, R.raw.ylzs);
                    player.setLooping(true);
                    player.start();
                }
                if(messageBody.contains("#*location*#")){
                    context.startService(new Intent(context, LocationService.class));
                }
            }
        }
    }
}

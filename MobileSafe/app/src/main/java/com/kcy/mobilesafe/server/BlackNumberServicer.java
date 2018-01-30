package com.kcy.mobilesafe.server;

//import com.android.internal.telephony;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.kcy.mobilesafe.db.dao.BlackNumberDao;
import com.kcy.mobilesafe.util.ServiceUitl;

import java.lang.reflect.Method;

/**
 * Created by kcy on 2017/5/31.
 */

public class BlackNumberServicer extends Service {
    BlackNumberDao mDao;
    private  InnerSmsReceiver innerSmsReceiver;
    private TelephonyManager mTM;
    private MyPhoneStateListener mPhoneStateListener;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //拦截短信
        //注册广播接收者
        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        innerSmsReceiver = new InnerSmsReceiver();
        //配置优先级
        intentFilter.setPriority(1000);
        registerReceiver(innerSmsReceiver,intentFilter);
        mTM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //2,监听电话状态
        mPhoneStateListener = new MyPhoneStateListener();
        mTM.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    }
    class MyPhoneStateListener extends PhoneStateListener {
        //3,手动重写,电话状态发生改变会触发的方法
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    //挂断电话 	aidl文件中去了
//				mTM.endCall();
                 //   endCall(incomingNumber);
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

   /* private void endCall(String phone) {
        int mode=mDao.getMode(phone);
        if(mode==2||mode==3){

            try {
                //1.获取service的字节码文件
                Class<?> clazz = Class.forName("android.os.ServiceManager");
                //获得方法
                //2,获取方法
                Method method = clazz.getMethod("getService", String.class);
                //3,反射调用此方法
                IBinder iBinder = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
                //4,调用获取aidl文件对象方法
                ITelephony iTelephony = ITelephony.Stub.asInterface(iBinder);
                //5,调用在aidl中隐藏的endCall方法
                iTelephony.endCall();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }*/

    class InnerSmsReceiver extends BroadcastReceiver{

          @Override
          public void onReceive(Context context, Intent intent) {
                //获取短信内容，和电话号码，且拦截模式为1和3
              // 获得短信内容
              Object[] objects = (Object[]) intent.getExtras().get("pdus");
              //遍历短信内容
               for (Object object:objects){
                   SmsMessage sms=SmsMessage.createFromPdu((byte[]) object);
                   //获得短信对象的基本信息
                   String  oringinatingAddress=sms.getOriginatingAddress();
                   String messageBody=sms.getMessageBody();

                   mDao = BlackNumberDao.getInstance(getApplicationContext());
                   int mode= mDao.getMode(oringinatingAddress);
                   Log.i("sms11", "onReceive: "+oringinatingAddress);
                   Log.i("sms11", "onReceive: "+mode);
                   if(mode==1||mode==3)  {
                       abortBroadcast();
                   }
               }

          }
      }
    @Override
    public void onDestroy() {
        if(innerSmsReceiver!=null){
            unregisterReceiver(innerSmsReceiver);
        }
        super.onDestroy();
    }
}

package com.example.kcy.servicetext;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;

import java.io.IOException;

import static android.R.attr.permission;

/**
 * Created by kcy on 2017/4/13.
 */

public class TextService extends Service {
    Context mcontext;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public void onCreate() {
        //获取电话管理者的实例
        // Check for permissions
        mcontext=this;
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        TelephonyManager tm= (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //注册监听器
        //listener  : PhoneStateListener的实例 event:
        tm.listen(new MyPhoneStateListenrer(),PhoneStateListener.LISTEN_CALL_STATE);
        super.onCreate();
    }
    private class MyPhoneStateListenrer extends PhoneStateListener{
        //当设备状态发送变化时调用
        MediaRecorder recorder;
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state){
                case  TelephonyManager.CALL_STATE_IDLE://空闲状态
                    if(recorder!=null){
                        recorder.stop();
                        recorder.reset();   // You can reuse the object by going back to setAudioSource() step
                        recorder.release(); // Now the object cannot be reused
                    }

                    break;
                case  TelephonyManager.CALL_STATE_RINGING://响铃状态
                    System.out.println("开始接听");
                    //获取类的实例
                    recorder = new MediaRecorder();

                    //设置音频的来源
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //zet
                    //设置输出格式
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    //设置音频的编码方式
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //保存的文件路径

                    recorder.setOutputFile( Environment.getExternalStorageDirectory().getPath() );
                    try {
                        //准备录音
                        recorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    break;
                case  TelephonyManager.CALL_STATE_OFFHOOK://接听状态
                    System.out.println("接听状态");
                    recorder.start();   // Recording is now started
                    System.out.println("接听成功");
                    break;
                default:break;
            }

            super.onCallStateChanged(state, incomingNumber);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}

package com.kcy.mobilesafe.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kcy.mobilesafe.engine.ProcessInfoProvider;

/**
 * Created by kcy on 2017/6/3.
 */

public class LockSreenService extends Service{
    private IntentFilter intentFilter;
    private InnerReciver innerReciver;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        innerReciver=new InnerReciver();
        registerReceiver(innerReciver,intentFilter);

    }
    class InnerReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //清理进程
            ProcessInfoProvider.killAllProcess(context);
        }
    }

    @Override
    public void onDestroy() {
        if(innerReciver!=null){
            unregisterReceiver(innerReciver);
        }
        super.onDestroy();
    }
}

package com.example.kcy.servicetext;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by kcy on 2017/4/14.
 */

public class Text2Service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new PraintInt();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    public void printint(int shuju){
        Toast.makeText(getApplicationContext(),shuju,Toast.LENGTH_SHORT).show();
    }

}
 class PraintInt extends Binder {
    public void  printint( int shuju){
        printint(shuju);
    }
}

package com.example.kcy.remote_aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import android.support.annotation.Nullable;

/**
 * Created by kcy on 2017/4/16.
 */

public class RemoteRervicer extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    public void testMethod(){
        System.out.println("我是远程服务里的方法");
    }
    private class MyBinder extends  Iservices.Stub {


        @Override
        public void callTextMethod() {
            testMethod();
        }
    }
}

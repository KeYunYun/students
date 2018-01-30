package com.kcy.mobilesafe.util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by kcy on 2017/5/30.
 */

public class ServiceUitl {
    public static boolean isRunning(Context ctx, String serviceName){
        //获得activityManager管理者对象，可以去获取当前手机的运行的所有服务
       ActivityManager mAM= (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        //获取手机中正在运行的服务
      List<ActivityManager.RunningServiceInfo> runningServiceInfos= mAM.getRunningServices(20);
        for(ActivityManager.RunningServiceInfo runningServiceInfo:runningServiceInfos){
            Log.i("servicerss", "isRunning: "+runningServiceInfo.service.getClassName());
            Log.i("servicerss", "isRunning: "+serviceName.equals(runningServiceInfo.service.getClassName()));
            if (serviceName.equals(runningServiceInfo.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}

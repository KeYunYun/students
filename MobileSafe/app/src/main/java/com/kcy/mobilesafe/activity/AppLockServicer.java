package com.kcy.mobilesafe.activity;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kcy.mobilesafe.db.dao.AppLockDao;

import java.util.List;

/**
 * Created by kcy on 2017/6/3.
 */

public class AppLockServicer extends Service{
    private AppLockDao mDo;
   private boolean isWatch;
    private List<String> packageNameList;
    private UsageStatsManager usageStatsManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        //开启一个死循环，进行监听
        mDo=AppLockDao.getInstance(this);
        isWatch=true;
        watch();
    }

    private void watch() {
        //子线程中开启
        new Thread(){
            @Override
            public void run() {
                packageNameList=mDo.findAll();
                super.run();
                while (isWatch){
                    //监听正在开启的应用，任务栈
                    //获得activity管理者对象
                    Log.i("AppLockServicer", "onCreate: ok");
                    ActivityManager am= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    //获得正在运行的任务栈
                   List<ActivityManager.RunningTaskInfo> runningTasks= am.getRunningTasks(1);
                    ActivityManager.RunningTaskInfo runningTaskInfo=runningTasks.get(0);
                    //获得栈顶的activity,在获得包名
                   String Packagename= runningTaskInfo.topActivity.getPackageName();
                    Log.i("intent.putExtra", "run: "+Packagename);
                    //拿此包名与数据库中的名称进行比较
                    if(packageNameList.contains(Packagename)){
                        //弹出拦截界面
                        Intent intent=new Intent(new Intent(getApplicationContext(),EnterPwdActivity.class));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("packagename",Packagename);
                        startActivity(intent);

                    }else {

                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isWatch=false;
    }
    private String getRunningApp() {
        long ts = System.currentTimeMillis();
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST,ts-2000, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return null;
        }
        UsageStats recentStats = null;
        for (UsageStats usageStats : queryUsageStats) {
            if (recentStats == null ||
                    recentStats.getLastTimeUsed() < usageStats.getLastTimeUsed()) {
                recentStats = usageStats;
            }
        }
        return recentStats.getPackageName();
    }
}

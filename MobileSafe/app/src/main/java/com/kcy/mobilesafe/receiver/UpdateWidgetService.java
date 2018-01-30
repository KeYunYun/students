package com.kcy.mobilesafe.receiver;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.RemoteViews;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.engine.ProcessInfoProvider;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kcy on 2017/6/3.
 */

 public class UpdateWidgetService  extends Service{
    Timer timer;
    InnerReciver inner;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //管理
        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);


        inner = new InnerReciver();
        registerReceiver(inner,intentFilter);

    }
    class InnerReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
                startTimer();
            }else {
                cancelTimerTask();
            }
        }
    }

    private void cancelTimerTask() {
        if(timer!=null) {
            timer.cancel();
            timer=null;
        }
    }

    private void startTimer() {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // ui的更新
                Log.i("updateAppWidget", "5秒一次的定时任务现在正在运行..........");
                updateAppWidget();
            }
        }, 0, 5000);
    }

    private void updateAppWidget() {
        //1.获取AppWidget对象
        AppWidgetManager aWM = AppWidgetManager.getInstance(this);
        //2.获取窗体小部件布局转换成的view对象(定位应用的包名,当前应用中的那块布局文件)
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.process_widget);
        //3.给窗体小部件布view对象,内部控件赋值
        remoteViews.setTextViewText(R.id.tv_process_count, "进程总数:"+ProcessInfoProvider.getProcessCount(this));
        //4.显示可用内存大小
        String strAvailSpace = Formatter.formatFileSize(this, ProcessInfoProvider.getAvailSpace(this));
        remoteViews.setTextViewText(R.id.tv_process_memory, "可用内存:"+strAvailSpace);


        //点击窗体小部件，进入应用
        //调用延期意图
        Intent intent =new Intent("android.intent.action.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        PendingIntent pendingintnet= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.ll_root,pendingintnet);

        //通过延期意图发送广播
       Intent broadIntent= new Intent("android.intent.action.KILL_BACKGROUND_PROCESS");
        PendingIntent broad= PendingIntent.getBroadcast(this,0,broadIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_clear,broad);

        //上下文环境,窗体小部件对应广播接受者的字节码文件
        ComponentName componentName = new ComponentName(this,MyWidget.class);
        //更新窗体小部件
        aWM.updateAppWidget(componentName, remoteViews);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(inner!=null){
            unregisterReceiver(inner);
        }
        cancelTimerTask();
    }
}

package com.kcy.mobilesafe.receiver;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by kcy on 2017/6/3.
 */

public class MyWidget extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override//当窗体小部件的宽高发生改变，创建一个窗体也会调用
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        context.startService(new Intent(context,UpdateWidgetService.class));
    }

    @Override//创建第一个窗体小部件调用的方法
    public void onEnabled(Context context) {
        context.startService(new Intent(context,UpdateWidgetService.class));
        super.onEnabled(context);
    }

    @Override//删除最后一个小部件调用的方法
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.stopService(new Intent(context,UpdateWidgetService.class));
    }

    @Override//删除一个窗体调用的方法
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    @Override//创建多一个窗体小部件调用的方法
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        context.startService(new Intent(context,UpdateWidgetService.class));
    }
}

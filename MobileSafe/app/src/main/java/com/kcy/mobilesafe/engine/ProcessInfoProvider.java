package com.kcy.mobilesafe.engine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.view.animation.Animation;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.activity.ProssessManagerActivity;
import com.kcy.mobilesafe.db.domain.ProcessInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcy on 2017/6/2.
 */

public class ProcessInfoProvider {
    //获取进程总数
    public static int getProcessCount(Context context) {
        //1,获取activityManager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //2,获取正在运行进程的集合
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        //3,返回集合的总数
        return runningAppProcesses.size();
    }

    public static long getAvailSpace(Context context){
        //获得activityMAnager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //构建可以内存的对象
        ActivityManager.MemoryInfo memoryInfo= new ActivityManager.MemoryInfo();
        //赋值
        am.getMemoryInfo(memoryInfo);
        //获取响应的可用内存大小
      return   memoryInfo.availMem;

    }
    public static long getTotalSpace(Context context){
        //获得activityMAnager
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //构建可以内存的对象
        ActivityManager.MemoryInfo memoryInfo= new ActivityManager.MemoryInfo();
        //赋值
        am.getMemoryInfo(memoryInfo);
        //获取响应的可用内存总数
        return   memoryInfo.totalMem;

    }
    public static List<ProcessInfo> getProcessInfoList(Context context){
        //获得进程相关信息
        //获得activityManager对象

        List<ProcessInfo> processInfoList=new ArrayList<ProcessInfo>();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得正在运行的集合
        List<ActivityManager.RunningAppProcessInfo> runningAppProcess = am.getRunningAppProcesses();
        //获取packmanager对象
        PackageManager pm=context.getPackageManager();
        //循环遍历进程的信息
        for (ActivityManager.RunningAppProcessInfo info:runningAppProcess){
           ProcessInfo processInfo= new ProcessInfo();
            //获取经常的名称
          processInfo.packageName=  info.processName;
            //获取经常占用的内存pid数组
          Debug.MemoryInfo[] processMemory= am.getProcessMemoryInfo(new int[]{info.pid});
            //获得以使用的大小
         Debug.MemoryInfo memoryInfo= processMemory[0];
            processInfo.memsize=memoryInfo.getTotalPrivateDirty()*1024;
            //获得运用的名称
            try {
                ApplicationInfo applicationInfo=pm.getApplicationInfo(processInfo.packageName,0);
                processInfo.name=  applicationInfo.loadLabel(pm).toString();
                processInfo.icon=applicationInfo.loadIcon(pm);
                //判断是否为系统进程
                if((applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==ApplicationInfo.FLAG_SYSTEM){
                    processInfo.isSystem=true;
                }else {
                    processInfo.isSystem=false;
                }
            } catch (PackageManager.NameNotFoundException e) {
                processInfo.name=info.processName;
                processInfo.icon=context.getResources().getDrawable(R.drawable.ic_launcher);
                processInfo.isSystem=true;
                e.printStackTrace();
            }
            processInfoList.add(processInfo);
        }
        return processInfoList;
    }


        //1,获取
        public static void killProcess(Context ctx,ProcessInfo processInfo) {
            //1,获取activityManager
            ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
            //2,杀死指定包名进程(权限)
            am.killBackgroundProcesses(processInfo.packageName);
        }

    public static void killAllProcess(Context ctx) {
        //1,获取activityManager
        //1,获取activityManager
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        //2,获取正在运行进程的集合
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo info : runningAppProcesses){
                if(info.processName.equals(ctx.getPackageName())){
                    continue;
                }
                am.killBackgroundProcesses(info.processName);
        }

    }
}

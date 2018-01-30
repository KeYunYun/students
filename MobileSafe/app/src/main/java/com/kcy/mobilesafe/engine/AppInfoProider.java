package com.kcy.mobilesafe.engine;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.kcy.mobilesafe.db.domain.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcy on 2017/6/2.
 */

public class AppInfoProider {
    public static List<AppInfo> getAppInfoList(Context context){
        //1.包的管理者对象
        PackageManager pm=context.getPackageManager();
        //获取安装在手机上的运用的相关信息
        List<PackageInfo> packageInfosList=pm.getInstalledPackages(0);
        //循环遍历
        List<AppInfo> appInfoList =new ArrayList<AppInfo>();
        for(PackageInfo packageInfo:packageInfosList){
            AppInfo appInfo=new AppInfo();
            //获得应用的包名
            appInfo.packName=packageInfo.packageName;
            //获得应用的名称
            ApplicationInfo applicationInfo=packageInfo.applicationInfo;
            appInfo.name=applicationInfo.loadLabel(pm).toString();
            //获得图标
            appInfo.icon=applicationInfo.loadIcon(pm);
            //判断是非是系统应用
            if((applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==ApplicationInfo.FLAG_SYSTEM){
                appInfo.isSystem=true;
            }else{
                appInfo.isSystem=false;
            }
            //是否安装在sd
            if((applicationInfo.flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)==ApplicationInfo.FLAG_EXTERNAL_STORAGE){
                appInfo.isSdCard=true;
            }else {
                appInfo.isSdCard=false;
            }
            appInfoList.add(appInfo);
        }
        return appInfoList;
    }
}

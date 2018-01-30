package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kcy.mobilesafe.R;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kcy on 2017/6/5.
 */

public class CacheClearActivity extends Activity {
    private static final int UPDATE_CACHE_DATE = 1000;
    private static final int CHECK_CAHE_APP = 100;
    private static final int CHECK_FINSH =10 ;
    private static final int CLEAR_CACHE =11 ;
    private Button bt_clear;
    private ProgressBar pb_bar;
    private TextView tv_name;
   private LinearLayout ll_add_text;
    private  String packagename;
    private   PackageManager mPm;
    private int index=1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_CACHE_DATE:
                    //添加条目
                    View view = View.inflate(getApplicationContext(), R.layout.linearlayout_cache_item, null);

                    ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                    TextView tv_item_name = (TextView) view.findViewById(R.id.tv_name);
                    TextView tv_memory_info = (TextView)view.findViewById(R.id.tv_memory_info);
                    ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
                    final CacheInfo cachinfo =(CacheInfo) msg.obj;
                    iv_icon.setBackgroundDrawable(cachinfo.icon);
                    tv_item_name.setText(cachinfo.name);
                    tv_memory_info.setText(Formatter.formatFileSize(getApplicationContext(), cachinfo.cachesize));
                    ll_add_text.addView(view,0);
                    iv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //清除单个选中应用的缓存内容(PackageMananger)

						/* 以下代码如果要执行成功则需要系统应用才可以去使用的权限
						 * android.permission.DELETE_CACHE_FILES
						 * try {
							Class<?> clazz = Class.forName("android.content.pm.PackageManager");
							//2.获取调用方法对象
							Method method = clazz.getMethod("deleteApplicationCacheFiles", String.class,IPackageDataObserver.class);
							//3.获取对象调用方法
							method.invoke(mPm, cacheInfo.packagename,new IPackageDataObserver.Stub() {
								@Override
								public void onRemoveCompleted(String packageName, boolean succeeded)
										throws RemoteException {
									//删除此应用缓存后,调用的方法,子线程中
									Log.i(tag, "onRemoveCompleted.....");
								}
							});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
                            //源码开发课程(源码(handler机制,AsyncTask(异步请求,手机启动流程)源码))
                            //通过查看系统日志,获取开启清理缓存activity中action和data
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.parse("package:"+cachinfo.pachagename));
                            startActivity(intent);
                        }
                    });
                    break;
                case CHECK_CAHE_APP:
                    tv_name.setText((String)msg.obj);
                    break;
                case CHECK_FINSH:
                    tv_name.setText("扫描完成");
                    break;
                case CLEAR_CACHE:
                    tv_name.setText("清理完成");
                    //移除所以条目
                    ll_add_text.removeAllViews();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_clear);
        initUI();
        initDate();
    }


    private void initDate() {
       new Thread(){
           @Override
           public void run() {
               super.run();
               // 遍历手机所有的运用获取有缓存的运用
               //获得包的管理者对象

               mPm = getPackageManager();
               //获得所有手机的运用
               List<PackageInfo> installedPackage=mPm.getInstalledPackages(0);
               //设置进度跳条的最大值
               pb_bar.setMax(installedPackage.size());
               //遍历每个运用，或的每个有缓存的运用的信息
               for(PackageInfo packinfo:installedPackage){

                   packagename = packinfo.packageName;
                   getPackageCahe(packagename);
                   try {
                       Thread.sleep(100);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   pb_bar.setProgress(index++);
                   //没循环一次 就将应用的名称发送给主线程更新
                   Message msg=Message.obtain();
                   msg.what= CHECK_CAHE_APP;
                   String name = null;
                   try {

                       name = mPm.getApplicationInfo(packagename,0).loadLabel(mPm).toString();
                   } catch (PackageManager.NameNotFoundException e) {
                       e.printStackTrace();
                   }
                   msg.obj=name;
                   mHandler.sendMessage(msg);
               }
               Message msg=  Message.obtain();
               msg.what=CHECK_FINSH;
               mHandler.sendMessage(msg);
           }
       }.start();
    }

    class CacheInfo{
        public  String name;
        public Drawable icon;
        public  String pachagename;
        public  long cachesize;
    }
    private void getPackageCahe(String packagename) {
        IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {

            public void onGetStatsCompleted(PackageStats stats,
                                            boolean succeeded) {
                    //获得指定包名的缓存大小
                long cacheSize= stats.cacheSize;
                //判断缓存的大小是否大于0
                if(cacheSize>0){
                    //告诉主线程更新UI
                    Message msg=Message.obtain();

                    msg.what=UPDATE_CACHE_DATE;
                    //维护有缓存的info
                    CacheInfo cacheinfo=new CacheInfo();
                    cacheinfo.cachesize=cacheSize;
                    cacheinfo.pachagename=stats.packageName;
                    try {
                        cacheinfo.name=mPm.getApplicationInfo(stats.packageName,0).loadLabel(mPm).toString();
                        cacheinfo.icon=mPm.getApplicationInfo(stats.packageName,0).loadIcon(mPm);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    msg.obj=cacheinfo;
                    mHandler.sendMessage(msg);
                }
            }
        };
        //获得缓存信息
        //1.获取指定类的字节码文件
        try {
            Class<?> clazz = Class.forName("android.content.pm.PackageManager");
            //2.获取调用方法对象
            Method method = clazz.getMethod("getPackageSizeInfo", String.class,IPackageStatsObserver.class);
            //3.获取对象调用方法
            method.invoke(mPm, packagename,mStatsObserver);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void initUI() {
        bt_clear= (Button) findViewById(R.id.bt_clear);
        pb_bar= (ProgressBar) findViewById(R.id.pb_bar);
        tv_name= (TextView) findViewById(R.id.tv_name);
        ll_add_text= (LinearLayout) findViewById(R.id.ll_add_text);
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.获取指定类的字节码文件
                try {
                    Class<?> clazz = Class.forName("android.content.pm.PackageManager");
                    //2.获取调用方法对象
                    Method method = clazz.getMethod("freeStorageAndNotify", long.class,IPackageDataObserver.class);
                    //3.获取对象调用方法
                    method.invoke(mPm, Long.MAX_VALUE,new IPackageDataObserver.Stub() {
                        @Override
                        public void onRemoveCompleted(String packageName, boolean succeeded)
                                throws RemoteException {
                            //清除缓存完成后调用的方法(考虑权限)
                            Message msg = Message.obtain();
                            msg.what = CLEAR_CACHE;
                            mHandler.sendMessage(msg);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

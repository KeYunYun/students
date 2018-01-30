package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.engine.VirusDao;
import com.kcy.mobilesafe.util.Md5Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kcy on 2017/6/4.
 */

public class AnitVirusActivity extends Activity{
    private   ImageView iv_scanning;
    private TextView   tv_name;
    private  ProgressBar pb_bar;
    private  LinearLayout ll_add_text;
    private  RotateAnimation rotate;
    private int index=1;
    private  List<ScanInfo> viruScanInfoList;
    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SCANING:
                    ScanInfo indo=(ScanInfo)msg.obj;
                    tv_name.setText(indo.name);
                   TextView textview= new TextView(getApplicationContext());
                    if(indo.isVirus){
                        textview.setTextColor(Color.RED);
                        textview.setText("发现病毒"+indo.name);
                    }else {
                        textview.setTextColor(Color.BLACK);
                        textview.setText("扫描安全"+indo.name);
                    }
                    ll_add_text.addView(textview);
                    break;
                case SCANING_Finally:
                    tv_name.setText("扫描完成");
                    //停止动画
                    iv_scanning.clearAnimation();
                    //卸载包含病毒的运用
                    uninstallViurs();
                    break;
            }
        }
    };

    private void uninstallViurs() {
        for(ScanInfo scaninfo:viruScanInfoList){
            Intent intent = new Intent("android.intent.action.DELETE");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("package:"+scaninfo.packagename));
            startActivity(intent);
        }
    }

    private static final int SCANING=100;
    private static final int SCANING_Finally=10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_virus);

        initUI();
        initAnimation();
        checkVirus();
    }

    private void checkVirus() {
        new  Thread(){
            @Override
            public void run() {
                super.run();
                List<String> virusList = VirusDao.getVirusList();
                //获得手机上面的所有yuny的签名文件的MD5码
                //获得包的管理对象的
                PackageManager pm= getPackageManager();
                //获得包的签名文件
                List<PackageInfo> packageInfoList= pm.getInstalledPackages(PackageManager.GET_SIGNATURES +
                        PackageManager.GET_UNINSTALLED_PACKAGES);
                //遍历集合
                //记录所以运用的集合
                List<ScanInfo> scanInfoList=new ArrayList<ScanInfo>();

                viruScanInfoList = new ArrayList<ScanInfo>();
                pb_bar.setMax(packageInfoList.size());
                for(PackageInfo packageinfo:packageInfoList){
                    ScanInfo scaninfo= new ScanInfo();
                    //或的签名文件的数组
                    Signature[] signatures=packageinfo.signatures;
                    //获取签名文件的第一个
                    Signature signature=signatures[0];
                    String string =signature.toCharsString();
                    //获得签名的MD5
                    String encoder= Md5Util.encoder(string);
                    //比对运用是非是病毒
                    //创建记录病毒的集合

                    if(virusList.contains(encoder)){
                        //记录病毒
                        scaninfo.isVirus=true;
                        viruScanInfoList.add(scaninfo);
                    }else {
                        scaninfo.isVirus=false;
                    }

                    //维护对象的包名，运用
                    scaninfo.packagename=packageinfo.packageName;
                    scaninfo.name=packageinfo.applicationInfo.loadLabel(pm).toString();
                    scanInfoList.add(scaninfo);
                    //更新进度条
                    pb_bar.setProgress(index++);
                    //在子线程中发送消息，更新UI
                    Message msg= Message.obtain();
                    msg.what=SCANING;
                    msg.obj=scaninfo;
                    mHandler.sendMessage(msg);

                    try {
                        Thread.sleep(50+new Random().nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg= Message.obtain();
                msg.what=SCANING_Finally;
                mHandler.sendMessage(msg);
            }
        }.start();

    }
    class  ScanInfo{
        public boolean isVirus;
        public String packagename;
        public String name;
    }

    private void initAnimation() {

        rotate = new RotateAnimation(0,360,
                 Animation.RELATIVE_TO_SELF,0.5f,
                 Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);
        //指定动画一直旋转
        //rotate.setRepeatMode(RotateAnimation.INFINITE);
        rotate.setRepeatCount(RotateAnimation.INFINITE);
        rotate.setFillAfter(true);
        iv_scanning.startAnimation(rotate);
    }

    private void initUI() {
        iv_scanning  = (ImageView) findViewById(R.id.iv_scanning);
        tv_name = (TextView) findViewById(R.id.tv_name);
        ll_add_text = (LinearLayout) findViewById(R.id.ll_add_text);
        pb_bar = (ProgressBar) findViewById(R.id.pb_bar);

    }
}

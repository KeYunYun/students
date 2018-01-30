package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.server.AddressService;
import com.kcy.mobilesafe.server.BlackNumberServicer;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.ServiceUitl;
import com.kcy.mobilesafe.util.Sputils;
import com.kcy.mobilesafe.view.SettinfItemView;
import com.kcy.mobilesafe.view.SettingClickView;

/**
 * Created by kcy on 2017/4/25.
 */

public class SettingActivity extends Activity {
    String [] mToastStyleDes;
     SettingClickView scv_toast_style;
    int toast_style;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdate();
        initAddress();
        initToastStyle();
        initLoaction();
        initBlackNumber();
        initAppLock();

    }

    private void initAppLock() {
        final SettinfItemView   siv_lock= (SettinfItemView) findViewById(R.id.siv_lock);
        boolean isRunning= ServiceUitl.isRunning(getApplicationContext(),"com.kcy.mobilesafe.activity.AppLockServicer");
        siv_lock.setCheck(isRunning);
        siv_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheak= siv_lock.isCheck();
                siv_lock.setCheck(!isCheak);
                if(!isCheak){
                    startService(new Intent(getApplicationContext(), AppLockServicer.class));
                    Log.i("ischeak", "onClick: true");
                }else {
                    stopService(new Intent(getApplicationContext(), AppLockServicer.class));
                    Log.i("ischeak", "onClick: flase");
                }
            }
        });

    }

    private void initBlackNumber() {
        //设置黑名单
        final SettinfItemView siv_blackNumber= (SettinfItemView) findViewById(R.id.siv_blacknmber);
        boolean isRunning= ServiceUitl.isRunning(getApplicationContext(),"com.kcy.mobilesafe.server.BlackNumberServicer");
        siv_blackNumber.setCheck(isRunning);
        siv_blackNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheak= siv_blackNumber.isCheck();
                siv_blackNumber.setCheck(!isCheak);
                if(!isCheak){
                    startService(new Intent(getApplicationContext(), BlackNumberServicer.class));
                    Log.i("ischeak", "onClick: true");
                }else {
                    stopService(new Intent(getApplicationContext(), BlackNumberServicer.class));
                    Log.i("ischeak", "onClick: flase");
                }
            }
        });
    }

    private void initLoaction() {
        SettingClickView scv_loaction= (SettingClickView) findViewById(R.id.dcv_location);
        scv_loaction.setmTitle("归属地提示框的位置");
        scv_loaction.setmDes("设置归属地提示框的位置");
        scv_loaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ToastLoactionActivity.class));
            }
        });
    }

    private void initToastStyle() {
        scv_toast_style= (SettingClickView) findViewById(R.id.dcv_toast_style);
        scv_toast_style.setmTitle("电话归属地的显示风格");
        //创建String 数组
        mToastStyleDes=  new String[]{"透明","灰色","橙色","蓝色","绿色"};


        toast_style = Sputils.getInt(getApplicationContext(), ConstantValue.TOAST_STYLE,0);

        scv_toast_style.setmDes(mToastStyleDes[toast_style]);
        scv_toast_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastStyleDialog();
            }
        });

    }

    private void showToastStyleDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("请选择归属地的样式");
        builder.setIcon(R.drawable.ic_launcher);
        builder.setSingleChoiceItems(mToastStyleDes,  Sputils.getInt(getApplicationContext(), ConstantValue.TOAST_STYLE,0), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Sputils.putInt(getApplicationContext(),ConstantValue.TOAST_STYLE,which);
                dialog.dismiss();
                scv_toast_style.setmDes(mToastStyleDes[which]);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void initAddress() {

        final SettinfItemView siv_address= (SettinfItemView) findViewById(R.id.siv_address);
       boolean isRunning=   ServiceUitl.isRunning(getApplicationContext(),"com.kcy.mobilesafe.server.AddressService");
       siv_address.setCheck(isRunning);
        siv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck=siv_address.isCheck();
                siv_address.setCheck(!isCheck);
               // Sputils.putBoolean(getApplicationContext(),ConstantValue.);
                if(!isCheck){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!Settings.canDrawOverlays(getApplicationContext())) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getApplicationContext().getPackageName()));
                            startActivity(intent);
                        }
                    }
                    startService(new Intent(getApplicationContext(), AddressService.class));
                }else{
                    stopService(new Intent(getApplicationContext(),AddressService.class));
                }
            }
        });

    }

    private void initUpdate() {
        final SettinfItemView siv_update= (SettinfItemView) findViewById(R.id.siv_update);
        boolean open_update= Sputils.getBoolean(this, ConstantValue.OPEN_UPDATE,false);
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得之前的选中状态
                boolean cheak = siv_update.isCheck();
                siv_update.setCheck(!cheak);
                Log.d("123",""+cheak);
                Sputils.putBoolean(getApplicationContext(),ConstantValue.OPEN_UPDATE,!cheak);

            }
        });
    }
}

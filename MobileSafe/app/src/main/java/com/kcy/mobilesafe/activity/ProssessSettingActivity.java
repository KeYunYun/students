package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.server.LockSreenService;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.ServiceUitl;
import com.kcy.mobilesafe.util.Sputils;

/**
 * Created by kcy on 2017/6/3.
 */

public class ProssessSettingActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prossess_setting);
        initSystemShow();
        initLockScreenClear();
    }

    private void initLockScreenClear() {
        final CheckBox cb_lock_clear = (CheckBox) findViewById(R.id.cb_lock_clear);
        boolean isRunning= ServiceUitl.isRunning(getApplicationContext(),"com.kcy.mobilesafe.server.LockSreenService");
        if(isRunning){
            cb_lock_clear.setText("锁屏清理以开启 ");
        }else {
            cb_lock_clear.setText("锁屏清理以关闭");
        }
        cb_lock_clear.setChecked(isRunning);
        cb_lock_clear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_lock_clear.setText("锁屏清理以开启 ");
                    startService(new Intent(getApplicationContext(),LockSreenService.class));
                }else {
                    cb_lock_clear.setText("锁屏清理以关闭");
                    stopService(new Intent(getApplicationContext(),LockSreenService.class));
                }
                Sputils.putBoolean(getApplicationContext(), ConstantValue.SHOW_SYSTEM,isChecked);
            }
        });

    }

    private void initSystemShow() {
        final CheckBox cb_show_system = (CheckBox) findViewById(R.id.cb_show_system);
        //读取之前存储的状态
       boolean showsystem= Sputils.getBoolean(getApplicationContext(),ConstantValue.SHOW_SYSTEM,false);
        cb_show_system.setChecked(showsystem);
        if(showsystem){
            cb_show_system.setText("显示系统进程 ");
        }else {
            cb_show_system.setText("隐藏进程");
        }
        //对选中状态进行监听
        cb_show_system.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_show_system.setText("显示系统进程 ");
                }else {
                    cb_show_system.setText("隐藏进程");
                }
                Sputils.putBoolean(getApplicationContext(), ConstantValue.SHOW_SYSTEM,isChecked);
            }
        });
    }
}

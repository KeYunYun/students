package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.engine.SmsBackUp;

import java.io.File;

/**
 * Created by kcy on 2017/5/23.
 */

public class AToolActivity extends Activity {
    private TextView tv_query_phoneAddress;
    private   TextView tv_sms_backup;
    private  TextView tv_commonnumber_query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atool);
        initphoneAddress();
        //短信备份
        initSmsBackUp();
        //常用号码查询
        initCommonNumberQuery();
        initAppLock();
    }

    private void initAppLock() {
        TextView tv_app_lock= (TextView) findViewById(R.id.tv_app_lock);
        tv_app_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AppLocakActivity.class));
            }
        });
    }

    private void initCommonNumberQuery() {

        tv_commonnumber_query = (TextView) findViewById(R.id.tv_commomnumber_query);
        tv_commonnumber_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CommonNumberQueryActivity.class));
            }
        });
    }

    private void initSmsBackUp() {

        tv_sms_backup = (TextView) findViewById(R.id.tv_sms_backup);
        tv_sms_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSmsbackupdiolag();
            }
        });
    }

    private void showSmsbackupdiolag() {
        final ProgressDialog progressdialog=new ProgressDialog(this);
        progressdialog.setIcon(R.drawable.ic_launcher);
        progressdialog.setTitle("短信备份");
        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //展现
        progressdialog.show();
        //短信的获取
        //找到短信所在的数据库
        //使用内容提供者去获得数据
        new Thread(){
            @Override
            public void run() {
            String path=    Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"sms.xml";
                SmsBackUp.bachUp(getApplicationContext(), path,
                        new SmsBackUp.CallBack() {
                    @Override
                    public void setMax(int max) {
                        progressdialog.setMax(max);
                    }

                    @Override
                    public void setProgress(int index) {
                        progressdialog.setProgress(index);
                    }
                });
                progressdialog.dismiss();
                super.run();
            }
        }.start();


    }

    private void initphoneAddress() {
        tv_query_phoneAddress= (TextView) findViewById(R.id.tv_query_phone_address);
        tv_query_phoneAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),QueryPhoneAdressActivity.class));
            }
        });
    }

}

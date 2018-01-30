package com.example.kcy.baidumusic;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
     Iservice iservice;
    Mycoun conn;
    Button bt_start;
    Button bt_stop;
    Button bt_starting;
    static SeekBar sbar;

    public static Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle date=msg.getData();
           int   dration =date.getInt("duration");
             int currentPostion=date.getInt("currentPostion");
            System.out.println(currentPostion);
            System.out.println(dration);
            sbar.setMax(dration);
            sbar.setProgress(currentPostion);

        }
    };
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_start= (Button) findViewById(R.id.strat);
        bt_start.setOnClickListener(this);
        bt_stop= (Button) findViewById(R.id.stop);
        bt_stop.setOnClickListener(this);
        bt_starting= (Button) findViewById(R.id.starting);
        bt_starting.setOnClickListener(this);
        sbar= (SeekBar) findViewById(R.id.seekBar);
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        System.out.println(permissionCheck);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                System.out.println("里面"+ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE));
            }
        }
        Intent intent =new Intent(this,MusicService.class);

        //先调用startservice开启服务
        startService(intent);
        //调用BinderService会的Binder对象

        conn=new Mycoun();
        bindService(intent,conn,BIND_AUTO_CREATE);
        sbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //停止拖动执行
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
            //开始推动
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                iservice.callToPlay(seekBar.getProgress());
            }
        });
    }
    private class Mycoun implements ServiceConnection {
        //当链接时调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iservice= (Iservice) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.strat:
                clickStart();
                break;
            case R.id.stop:
                clickStop();
                break;
            case R.id.starting:
                clisckStarting();
                break;
            default:break;
        }
    }

    private void clisckStarting() {
        iservice.CallRePlaymusic();
    }

    private void clickStop() {
        iservice.CallPausemusic();
    }

    private void clickStart() {
        iservice.CallPlaymusic();
    }

    @Override
    protected void onDestroy() {
       // 在activity销毁的时候，注册service
       unbindService(conn);
        super.onDestroy();
    }
}

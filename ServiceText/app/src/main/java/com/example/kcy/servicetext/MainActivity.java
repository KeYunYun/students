package com.example.kcy.servicetext;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.permission;

public class MainActivity extends AppCompatActivity {
    PraintInt isservicr;
    Button bt_start;
    Button bt_shut;
    Button bt_bind;
    Context mcontext;
    MyCoun coun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_start= (Button) findViewById(R.id.bt_start);
        bt_shut=(Button)findViewById(R.id.bt_shut);
        mcontext=this;
        Intent intent =new Intent(mcontext,Text2Service.class);
        //链接到服务
        coun =new MyCoun();
        bindService(intent,coun,BIND_AUTO_CREATE);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启服务
                System.out.println("开启服务");
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    String[] PERMISSIONS_STORAGE = {
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    };
                    int REQUEST_EXTERNAL_STORAGE = 1;

                    ActivityCompat.requestPermissions(
                            (Activity) mcontext,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                }
                Intent intent=new Intent(mcontext,TextService.class);
                startService(intent);
            }
        });

       bt_bind= (Button) findViewById(R.id.bt_bind);
        bt_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              isservicr.printint(789);
            }
        });

    }
    //用来监听服务的状态
    private class MyCoun implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isservicr= (PraintInt) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    }

    @Override
    protected void onDestroy() {
        //activity销毁时取消绑定
        unbindService(coun);
        super.onDestroy();
    }
}

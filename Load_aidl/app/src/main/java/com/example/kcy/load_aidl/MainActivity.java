package com.example.kcy.load_aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kcy.remote_aidl.Iservices;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    Iservices iservices;
    Button button;
    Mycoun coun;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
       //通过隐式意图调用
        Intent intent=new Intent();
        //设置action
        intent.setAction("remoteaidl");
        coun=new Mycoun();
        bindService(intent, coun,BIND_AUTO_CREATE);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        unbindService(coun);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    private class Mycoun implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获得Binder对象
            iservices= Iservices.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                iservices.callTextMethod();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}

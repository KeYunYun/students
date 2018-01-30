package com.example.kcy.viewanimatiom;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bt_tm;
    Button bt_xz;
    Button bt_sf;
    Button bt_wy;
    ImageView iv;
    NotificationManager nm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_sf= (Button) findViewById(R.id.bt_sf);
        bt_sf.setOnClickListener(this);
        bt_xz= (Button) findViewById(R.id.bt_xz);
        bt_xz.setOnClickListener(this);
        bt_tm= (Button) findViewById(R.id.bt_tm);
        bt_tm.setOnClickListener(this);
        bt_wy= (Button) findViewById(R.id.bt_wy);
        bt_wy.setOnClickListener(this);
        iv= (ImageView) findViewById(R.id.imageView);
        iv.setOnClickListener(this);
        nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    public void onClick1(){


    }
    public void onClick2(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_xz:
                RotateAnimation ra=new RotateAnimation(0,360);
                ra.setDuration(2000);
                ra.setRepeatCount(1);
                ra.setRepeatMode(Animation.REVERSE);
                iv.startAnimation(ra);
                break;
            case R.id.bt_tm:
               Animation aa=  AnimationUtils.loadAnimation(getApplicationContext(),R.anim.aplat);
                iv.startAnimation(aa);
                break;
            case R.id.bt_wy:
                TranslateAnimation ta=new TranslateAnimation(Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,0.2f);
                ta.setDuration(2000);
                ta.setFillEnabled(true);
                iv.startAnimation(ta);
                break;
            case R.id.bt_sf:
                ScaleAnimation sa=new ScaleAnimation(1.0f,2.0f,1.0f,2.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                sa.setDuration(2000);
                sa.setRepeatCount(2);
                sa.setRepeatMode(Animation.REVERSE);
                iv.startAnimation(sa);
                break;
            case R.id.imageView:
                break;
        }
    }
}

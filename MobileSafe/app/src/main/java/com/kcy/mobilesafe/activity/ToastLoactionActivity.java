package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;

/**
 * Created by kcy on 2017/5/30.
 */

public class ToastLoactionActivity  extends Activity{
    private long[] mHits=new long[2];
    ImageView iv_drag;
    WindowManager mWM;
    Button bt_top;
    Button bt_tottom;
    int screenHeight;
    int scrernWight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_loactiom);
        initUI();
    }

    private void initUI() {

        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        screenHeight = mWM.getDefaultDisplay().getHeight();
        scrernWight = mWM.getDefaultDisplay().getWidth();
        iv_drag = (ImageView) findViewById(R.id.iv_drag);
        bt_top = (Button) findViewById(R.id.bt_top);
        bt_tottom = (Button) findViewById(R.id.bt_bottom);
        int locationx=  Sputils.getInt(getApplicationContext(), ConstantValue.LOCATION_X,0);
       int locationy= Sputils.getInt(getApplicationContext(), ConstantValue.LOCATION_Y,0);

        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT
                ,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin=locationx;
        layoutParams.topMargin=locationy;
        iv_drag.setLayoutParams(layoutParams);

        if(locationy>screenHeight/2){
            bt_tottom.setVisibility(View.INVISIBLE);
            bt_top.setVisibility(View.VISIBLE);
        }else{
            bt_tottom.setVisibility(View.VISIBLE);
            bt_top.setVisibility(View.INVISIBLE);
        }
        iv_drag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.arraycopy(mHits,1,mHits,0,mHits.length-1);
                mHits[mHits.length-1]= SystemClock.uptimeMillis();
                if(mHits[mHits.length-1]-mHits[0]<500){
                    int left = scrernWight/2 - iv_drag.getWidth()/2;
                    int top = screenHeight/2 - iv_drag.getHeight()/2;
                    int right = scrernWight/2+iv_drag.getWidth()/2;
                    int bottom = screenHeight/2+iv_drag.getHeight()/2;

                    //控件按以上规则显示
                    iv_drag.layout(left, top, right, bottom);

                    //存储最终位置
                    Sputils.putInt(getApplicationContext(), ConstantValue.LOCATION_X, iv_drag.getLeft());
                    Sputils.putInt(getApplicationContext(), ConstantValue.LOCATION_Y, iv_drag.getTop());
                }
            }
        });

        iv_drag.setOnTouchListener(new View.OnTouchListener() {
            int startY = 0;
            int startX = 0;
            int disX;
            int disY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int moveX= (int) event.getRawX();
                        int moveY= (int) event.getRawY();
                        disX=moveX-startX;
                        disY =moveY-startY;

                        //获得屏幕边距
                        int left=iv_drag.getLeft() +disX;
                        int top =iv_drag.getTop()+disY;
                        int right=iv_drag.getRight()+disX;
                        int botton=iv_drag.getBottom()+disY;

                        //容错处理(iv_drag不能拖拽出手机屏幕)
                        //左边缘不能超出屏幕
                        if(left<0){
                            return true;
                        }

                        //右边边缘不能超出屏幕
                        if(right>scrernWight){
                            return true;
                        }

                        //上边缘不能超出屏幕可现实区域
                        if(top<0){
                            return true;
                        }

                        //下边缘(屏幕的高度-22 = 底边缘显示最大值)
                        if(botton>screenHeight - 22){
                            return true;
                        }
                        if(top>screenHeight/2){
                            bt_tottom.setVisibility(View.INVISIBLE);
                            bt_top.setVisibility(View.VISIBLE);
                        }else {
                            bt_tottom.setVisibility(View.VISIBLE);
                            bt_top.setVisibility(View.INVISIBLE);
                        }
                        iv_drag.layout(left,top,right,botton);

                       startX= (int) event.getRawX();
                        startY= (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        Sputils.putInt(getApplicationContext(), ConstantValue.LOCATION_X,iv_drag.getLeft());
                        Sputils.putInt(getApplicationContext(), ConstantValue.LOCATION_Y,iv_drag.getTop());
                        break;
                }
                //既要相应点击事件，又要相应拖拽时间，放回false
                return false;
            }
        });
    }
}

package com.kcy.mobilesafe.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.engine.AddressDao;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;

/**
 * Created by kcy on 2017/5/30.
 */

public class AddressService extends Service{
    TelephonyManager mTm;
    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    InnerOutCallReceiver innerOutCallReceiver;
    int screenHeight;
    int scrernWight;
    WindowManager mWM;
    int[] mDrawrable;
    String address;
    TextView tv_toast;
    View mToast;
    MyPhoneStateListener myPhoneStateListener;
    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_toast.setText(address);
        }
    };
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //电话状态的监听，响铃显示，挂断停止
        //电话管理者对象
        mTm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //监听电话对象

        myPhoneStateListener = new MyPhoneStateListener();
        mTm.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        //获得窗体对象

        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
        screenHeight = mWM.getDefaultDisplay().getHeight();
        scrernWight = mWM.getDefaultDisplay().getWidth();

        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);

        innerOutCallReceiver = new InnerOutCallReceiver();
        registerReceiver(innerOutCallReceiver,intentFilter);


    }
    class InnerOutCallReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String phone =getResultData();
            showToast(phone);
        }
    }

    class MyPhoneStateListener extends PhoneStateListener{
        //手重写，电话状态发生改变的触发方法

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    //电话空闲
                    if(mWM!=null&&mToast!=null){
                        mWM.removeView(mToast);
                    }

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                        //自少有一个电话活动
                    Log.i("ring", "一个电话。。。。。。");
                break;
                case TelephonyManager.CALL_STATE_RINGING:

               showToast(incomingNumber);

                break;
            }
        }



    }

    public void showToast(String num) {

        // Toast.makeText(getApplicationContext(),num,Toast.LENGTH_LONG).show();
        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE	默认能够被触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        //在响铃的时候显示吐司,和电话类型一致
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");

        //指定吐司的所在位置(将吐司指定在左上角)
        params.gravity = Gravity.LEFT+Gravity.BOTTOM;
        //将吐司挂在窗体上

        mToast = View.inflate(getApplicationContext(), R.layout.toast_view,null);

        tv_toast = (TextView) mToast.findViewById(R.id.tv_toast);

        mToast.setOnTouchListener(new View.OnTouchListener() {
            int startY = 0;
            int startX = 0;
            int disX;
            int disY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();
                        disX = moveX - startX;
                        disY = moveY - startY;

                        params.x=params.x+disX;
                        params.y=params.y-disY;

                        if (params.x<0){
                            params.x=0;
                        }
                        if (params.y<0){
                            params.y=0;
                        }
                        if(params.x>scrernWight-mToast.getWidth()){
                            params.x=scrernWight-mToast.getWidth();
                        }
                        if(params.y>screenHeight-mToast.getHeight()-22){
                            params.y=screenHeight-mToast.getHeight()-22;
                        }
                        mWM.updateViewLayout(mToast,params);
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        Sputils.putInt(getApplicationContext(), ConstantValue.LOCATION_X, params.x);
                        Sputils.putInt(getApplicationContext(), ConstantValue.LOCATION_Y,params.y );
                        break;


                }
                return true;
            }
        });

        mDrawrable = new int[]{R.drawable.call_locate_white,
                R.drawable.call_locate_gray,R.drawable.call_locate_orange,R.drawable.call_locate_blue,R.drawable.call_locate_green};

        int toastStyleIndex= Sputils.getInt(getApplicationContext(), ConstantValue.TOAST_STYLE,0);
        tv_toast.setBackgroundResource(mDrawrable[toastStyleIndex]);
        params.x= Sputils.getInt(getApplicationContext(),ConstantValue.LOCATION_X,0);
        params.y=Sputils.getInt(getApplicationContext(),ConstantValue.LOCATION_Y,0);
        // /在窗体上挂载
        mWM.addView(mToast,params);

        //来电号码的查询
        query(num);
    }

    private void query(final  String num) {
        new Thread(){
            @Override
            public void run() {
                super.run();

                address = AddressDao.getAddress(num);
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }


    public void onDestroy() {
        super.onDestroy();
        //取消监听
        if(mTm!=null && myPhoneStateListener!=null) {
            mTm.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        }if(innerOutCallReceiver!=null){
            unregisterReceiver(innerOutCallReceiver);
        }
    }
}

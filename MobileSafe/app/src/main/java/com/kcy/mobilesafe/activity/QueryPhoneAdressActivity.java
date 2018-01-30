package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.engine.AddressDao;

/**
 * Created by kcy on 2017/5/23.
 */

 public class QueryPhoneAdressActivity extends Activity{
    EditText et_phone_num;
    Button bt_quert;
    TextView tv_query_result;
    String address;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
         tv_query_result.setText(address);
        }
    };
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryphoneadress);
        initUI();
    }

    private void initUI() {
        et_phone_num= (EditText) findViewById(R.id.et_phone);
        bt_quert= (Button) findViewById(R.id.bt_query);
        tv_query_result= (TextView) findViewById(R.id.tv_query_result);

        bt_quert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String phone=  et_phone_num.getText().toString().trim();
                if(!TextUtils.isEmpty(phone)) {
                    query(phone);
                }else{
                   //手机振动效果
                  Vibrator vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(1000);//振动的时间

                }
            }


        });

        et_phone_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                query(et_phone_num.getText().toString().trim());
            }
        });

    }
    private void query(final String phone) {
        new Thread(){
            @Override
            public void run() {
              address = AddressDao.getAddress(phone);
                //消息机制
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
}

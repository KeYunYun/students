package com.kcy.mobilesafe.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;
import com.kcy.mobilesafe.view.FocusTextView;
import com.kcy.mobilesafe.view.SettinfItemView;

/**
 * Created by kcy on 2017/5/20.
 */

public class Setup2Activity extends Activity{
    private SettinfItemView siv_bound;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        initUI();
    }

    private void initUI() {
        siv_bound= (SettinfItemView) findViewById(R.id.siv_sim_bound);
        String sim_number= Sputils.getString(getApplicationContext(), ConstantValue.SIM_NUMBER,"");
        if(TextUtils.isEmpty(sim_number)){
            siv_bound.setCheck(false);
        }else{
            siv_bound.setCheck(true);
        }
        siv_bound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck =siv_bound.isCheck();

                siv_bound.setCheck(!isCheck);
                if(!isCheck){

                    if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(Setup2Activity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
                    }else{
                    //获得手机序列号
                    TelephonyManager manager= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String simnumber=manager.getSimSerialNumber();
                        Log.i("simnumber",simnumber+"");
                    Sputils.putString(getApplicationContext(),ConstantValue.SIM_NUMBER,simnumber);
                    }
                }else{
                    Sputils.remove(getApplicationContext(),ConstantValue.SIM_NUMBER);
                }
            }
        });
    }

    public void nextPage(View view){
       String serialnumber= Sputils.getString(getApplicationContext(),ConstantValue.SIM_NUMBER,"");
        if(!TextUtils.isEmpty(serialnumber)){

        Intent intent=new Intent(getApplicationContext(),Setup3Activity.class);
        startActivity(intent);
        finish();
            overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
        }
        else{
            Toast.makeText(getApplicationContext(),"请绑定sim卡号",Toast.LENGTH_SHORT).show();
        }
    }
    public void prePage(View view){
        Intent intent= new Intent(getApplicationContext(),Setup1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }
}

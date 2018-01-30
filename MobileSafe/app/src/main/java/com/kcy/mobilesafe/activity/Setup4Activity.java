package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;

/**
 * Created by kcy on 2017/5/20.
 */

public class Setup4Activity extends Activity{
    private CheckBox cb_box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        initUI();
    }

    private void initUI() {
        cb_box= (CheckBox) findViewById(R.id.cb_box);
       boolean open_security= Sputils.getBoolean(getApplicationContext(),ConstantValue.OPEN_SECURITY,false);
        if(open_security){
            cb_box.setText("安全设置已开启");
        }else {
            cb_box.setText("安全设置已关闭");
        }
        cb_box.setChecked(open_security);
        cb_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    cb_box.setText("安全设置已开启");
                }else {
                    cb_box.setText("安全设置已关闭");
                }
                Sputils.putBoolean(getApplicationContext(),ConstantValue.OPEN_SECURITY,isChecked);
            }
        });


    }

    public void prePage(View view){
        startActivity(new Intent(getApplicationContext(),Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }
    public void nextPage(View view){
       //
        boolean open_secrity= Sputils.getBoolean(getApplicationContext(),ConstantValue.OPEN_SECURITY,false);
        if(open_secrity) {
            startActivity(new Intent(getApplicationContext(), SetupOverActivity.class));
            finish();
            overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
            Sputils.putBoolean(this, ConstantValue.SETUP_OVER,true);
        }else {
            Toast.makeText(getApplicationContext(),"请设置开启",Toast.LENGTH_SHORT).show();
        }
    }
}

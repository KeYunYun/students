package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;

/**
 * Created by kcy on 2017/5/18.
 */

public class SetupOverActivity extends Activity {
   private TextView mtv_safe_number;
    private TextView mTv_rest_setup;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean setup_over= Sputils.getBoolean(this, ConstantValue.SETUP_OVER,false);
        if(setup_over){
            setContentView(R.layout.activity_setup_over);
            initUI();
        }else {
         Intent intent=   new Intent(this,Setup1Activity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initUI() {
        mtv_safe_number= (TextView) findViewById(R.id.tv_safe_number);
        mtv_safe_number.setText(Sputils.getString(getApplicationContext(),ConstantValue.CONTACT_PHONE,""));
        mTv_rest_setup= (TextView) findViewById(R.id.tv_rest_setup);
        mTv_rest_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=   new Intent(getApplicationContext(),Setup1Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

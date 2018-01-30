package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kcy.mobilesafe.R;

/**
 * Created by kcy on 2017/5/18.
 */

public class Setup1Activity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }
    public void nextPage(View view){
        Intent intent=new Intent(getApplicationContext(),Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
    }
}

package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.kcy.mobilesafe.R;


/**
 * Created by kcy on 2017/5/18.
 */

public class TextActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      TextView textview=  new TextView(this);
        textview.setText("123456");
        setContentView(textview);
    }

}

package com.kcy.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kcy.mobilesafe.R;

/**
 * Created by kcy on 2017/4/25.
 */

public class SettingClickView extends RelativeLayout {
    TextView tv_title;
    TextView tv_des;
    String mDeson;
    String mDesoff;
    String mTitle;
    public SettingClickView(Context context) {
        this(context,null);
    }

    public SettingClickView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.setting_click_view,this);
        //自定义组合控件text

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_des= (TextView) findViewById(R.id.tv_des);
    }

    public void setmTitle(String title){
        tv_title.setText(title);
    }
    public void setmDes(String des){
        tv_des.setText(des);
    }
}

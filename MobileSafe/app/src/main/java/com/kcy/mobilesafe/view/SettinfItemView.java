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

public class SettinfItemView extends RelativeLayout {
    CheckBox cb_box;
    TextView tv_des;
    String mDeson;
    String mDesoff;
    String mTitle;
    private final static String  NAMESPACE="http://schemas.android.com/apk/res/com.kcy.mobilesafe";
    public SettinfItemView(Context context) {
        this(context,null);
    }

    public SettinfItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettinfItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.setting_item_view,this);
        //自定义组合控件text
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
         tv_des= (TextView) findViewById(R.id.tv_des);
         cb_box= (CheckBox) findViewById(R.id.cb_box);
        //获得自定义属性的方法
        initAttrs(attrs);
        tv_title.setText(mTitle);
    }

    private void initAttrs(AttributeSet attrs) {
        Log.i("attr", "initAttrs: "+attrs.getAttributeCount());

        mTitle = attrs.getAttributeValue(NAMESPACE,"destitle");

        mDesoff = attrs.getAttributeValue(NAMESPACE,"desoff");

        mDeson = attrs.getAttributeValue(NAMESPACE,"deson");

    }

    public boolean isCheck(){

        return cb_box.isChecked();
    }
    public  void setCheck(boolean isCheck){
        cb_box.setChecked(isCheck);
        if(isCheck){
            tv_des.setText(mDesoff);
        }else {
            tv_des.setText(mDeson);
        }
    }

}

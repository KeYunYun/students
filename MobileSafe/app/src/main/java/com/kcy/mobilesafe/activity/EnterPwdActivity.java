package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kcy.mobilesafe.R;

/**
 * Created by kcy on 2017/6/4.
 */

public class EnterPwdActivity extends Activity{
    private String packagename;
    private Button bt_submit;
    private EditText et_psd;
    private TextView tv_app_name;
    private ImageView iv_app_icon;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_psd);
       packagename= getIntent().getStringExtra("packagename");
        initUI();
        initData();
    }

    private void initData() {
        //通过获得的包名获得运用信息
        PackageManager pm=getPackageManager();
        try{
            ApplicationInfo applictionInfo=pm.getApplicationInfo(packagename,0);
            Drawable icon=applictionInfo.loadIcon(pm);
            iv_app_icon.setBackgroundDrawable(icon);
            tv_app_name.setText(applictionInfo.loadLabel(pm).toString());
            bt_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String psd=et_psd.getText().toString().trim();
                    if(!TextUtils.isEmpty(psd)){
                        if(psd.equals("123")){
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initUI() {
        bt_submit= (Button) findViewById(R.id.bt_submit);
        et_psd= (EditText) findViewById(R.id.et_psd);
        tv_app_name= (TextView) findViewById(R.id.tv_app_name);
        iv_app_icon= (ImageView) findViewById(R.id.iv_app_icon);
    }
}

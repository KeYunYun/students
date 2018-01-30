package com.example.kcy.freamenttext;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt_wechat;
    Button bt_me;
    Button bt_find;
    Button bt_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_wechat= (Button) findViewById(R.id.bt_wechat);
        bt_wechat.setOnClickListener(this);
        bt_me= (Button) findViewById(R.id.bt_me);
        bt_me.setOnClickListener(this);
        bt_find= (Button) findViewById(R.id.bt_find);
        bt_find.setOnClickListener(this);
        bt_content= (Button) findViewById(R.id.bt_content);
        bt_content.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        android.app.FragmentManager manage=getFragmentManager();
        FragmentTransaction transation=manage.beginTransaction();
        switch (v.getId()){
            case R.id.bt_wechat:
                transation.replace(R.id.ll,new WechatFragment());
                olickwechat();
                break;
            case R.id.bt_me:
                transation.replace(R.id.ll,new MeFragment());
                olickme();
                break;
            case R.id.bt_content:
                transation.replace(R.id.ll,new ContentFragment());
                olickcontent();
                break;
            case R.id.bt_find:
                transation.replace(R.id.ll,new FindFragment());
                olickfind();
                break;
            default:break;
        }
        transation.commit();
    }

    private void olickfind() {
    }

    private void olickcontent() {
    }

    private void olickme() {
    }

    private void olickwechat() {
    }
}

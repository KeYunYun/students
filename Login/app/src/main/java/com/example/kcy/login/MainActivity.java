package com.example.kcy.login;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.util.Formatter;
import java.util.Map;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText ed_usename;
    EditText ed_pwd;
    CheckBox cb_ewm;
    Button bt_login;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件id
        mContext=this;
        ed_usename =(EditText) findViewById(R.id.ed_use);
        ed_pwd =(EditText) findViewById(R.id.ed_pwd);
        cb_ewm=(CheckBox) findViewById(R.id.cb_rem);
        bt_login =(Button) findViewById(R.id.bt_loign);
        //设置点击事件
        bt_login.setOnClickListener(this);
        Map<String,String > map =UseNamePwd.gerUserInfor(mContext);
        if(map!=null){
            String username=map.get("username");
            String password=map.get("password");
            ed_usename.setText(username);
            ed_usename.setText(password);
            cb_ewm.setChecked(true);//设置复选框选中状态
        }

    }
    //创建一个Handler
    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;


            switch (msg.what) {
                case 1://get
                    Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();

                    break;
                case 2://post

                    Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }

        };
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_loign:
                getlogin();
                break;
            default:break;
        }
    }


    String uername;
    String pwd;
    private void getlogin(){
        uername=ed_usename.getText().toString().trim();
        pwd =ed_pwd.getText().toString().trim();
        boolean isrem=cb_ewm.isChecked();

        //判断是否为空
        if(TextUtils.isEmpty(uername)||TextUtils.isEmpty(pwd)){
            Toast.makeText(mContext,"用户密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        Random random = new Random();
        int num = random.nextInt(10);
        if(num <6){
            //带着用户名密码请求服务器验证密码是否正确
            LoginHttpUtils.requestNetForPostLogin(handler,uername,pwd);
        }else{

            LoginHttpUtils.requestNetForGetLogin(handler,uername,pwd);
        }

    }


}

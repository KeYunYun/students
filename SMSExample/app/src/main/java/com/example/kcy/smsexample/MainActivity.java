package com.example.kcy.smsexample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    EditText ed_input;
    EditText ed_context;
    Button bt_smbit;
    Context mcontext;
    Button bt_add;
    Button bt_mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_input= (EditText) findViewById(R.id.et_input);
        ed_context= (EditText) findViewById(R.id.et_sms);
        bt_add= (Button) findViewById(R.id.bt_add);
        bt_smbit= (Button) findViewById(R.id.bt_send);
        bt_add.setOnClickListener(this);
        bt_smbit.setOnClickListener(this);
        bt_mode= (Button) findViewById(R.id.bt_mode);
        bt_mode.setOnClickListener(this);
        mcontext=this;

    }
    public void add_contart(){
        Intent intent=new Intent(mcontext,ContartActivity.class);
        startActivity(intent);
        startActivityForResult(intent,1);

    }
    public void sendsms(){
        String num_str= ed_input.getText().toString().trim();
        String content =ed_context.getText().toString().trim();
        //获得smsMAnager实例
        SmsManager smsManager=SmsManager.getDefault();
        //号码，，发送的内容，
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        smsManager.sendTextMessage(num_str,null,content,null,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 10){
            String phone=  data.getStringExtra("phone");
            ed_input.setText(phone);
        }else if(resultCode==20){
            String smsstr=data.getStringExtra("smscontent");
            ed_context.setText(smsstr);
        }
    }
    public void jupMode(){
        Intent intent =new Intent(mcontext,ModeActivity.class);
        startActivityForResult(intent,2);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_add:
                add_contart();
                break;
            case  R.id.bt_send:
                sendsms();
                break;
            case R.id.bt_mode:
                jupMode();
                break;
            default:break;
        }
    }
}

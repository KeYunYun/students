package com.example.kcy.broadtext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bt_order;
    Button bt_disorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_disorder= (Button) findViewById(R.id.bu_disorder);
        bt_order= (Button) findViewById(R.id.bt_order);
        bt_disorder.setOnClickListener(this);
        bt_order.setOnClickListener(this);
    }

    public void disorderSend(){
        Intent intent =new Intent();
        intent.setAction("customsBorad");
        intent.putExtra("name","新闻发送");
        System.out.println(intent.getStringExtra("name"));
        sendBroadcast(intent);
        //发送一条无序广播
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bu_disorder:
                disorderSend();
                break;
            case R.id.bt_order:

                orderSend();
            default:break;
        }
    }

    private void orderSend() {
        Intent intent =new Intent();
        intent.setAction("orderSend");
        //receiverPermission  接收权限
        //
        sendOrderedBroadcast(intent,null,new FinallyReceiver(),null,1,"发了数据",null);
        System.out.println("发送了数据");
    }
}

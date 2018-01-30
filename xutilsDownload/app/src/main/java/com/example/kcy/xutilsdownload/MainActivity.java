package com.example.kcy.xutilsdownload;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    Button bu_call;
    Button bu_layout;
    Button bu_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      bu_call= (Button) findViewById(R.id.bu_call);
        bu_call.setOnClickListener(this);
        bu_layout= (Button) findViewById(R.id.bu_layout);
        bu_layout.setOnClickListener(this);
        bu_show=(Button)findViewById(R.id.tb_show);
        bu_show.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bu_call:
                System.out.println(1223);
                clickCall();

                break;

            case R.id.bu_layout:
                clickLayout();
                break;
            case R.id.tb_show:
                clickshow();
                break;

            default:break;
        }
    }

    private void clickshow() {
        //创建意图对象
        Intent intent=new Intent(this,Text2Activity.class);
        //指定开启界面
       // intent.setClassName("Text2Activity","MainActivity");
        //开启activity
        startActivity(intent);
    }

    //
    public void clickCall(){
        //创建意图对象
        Intent intent =new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+119));
        //开启activity
        startActivity(intent);
    }
    public void clickLayout(){
        //创建意图
        Intent intent =new Intent();
        //设置意图对象action对象
        intent.setAction("TEXT");
        //指定category
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
    }

}

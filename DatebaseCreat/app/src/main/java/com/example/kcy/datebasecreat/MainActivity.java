package com.example.kcy.datebasecreat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext=this;
        //创建一个帮助类对象
        SqlliteOpean sqlliteOpean =new SqlliteOpean(mcontext);
        //调用getReadableDatabase方法，来测试初始化数据库的建立
       SQLiteDatabase db = sqlliteOpean.getReadableDatabase();
        findViewById(R.id.all).setOnClickListener(this);
        findViewById(R.id.del).setOnClickListener(this);
        findViewById(R.id.select).setOnClickListener(this);
        findViewById(R.id.updata).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        InfoDom infoDom =new InfoDom(mcontext);
        switch (v.getId()){

            case R.id.all :

                InfoBean infoBean=new InfoBean();
                infoBean.name="张三";
                infoBean.phone="123456";
                infoDom.add(infoBean);

                break;
            case R.id.del :
                break;
            case R.id.updata :
                InfoBean infoBean1=new InfoBean();
                infoBean1.phone="987654";
                infoBean1.name="李四";
                infoDom.update(infoBean1);
                break;
            case R.id.select :
                infoDom.select("张三");
                break;
            default:
                break;

        }
    }
}

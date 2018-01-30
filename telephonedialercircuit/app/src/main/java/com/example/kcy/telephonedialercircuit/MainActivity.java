package com.example.kcy.telephonedialercircuit;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到需要使用的控件
        EditText et_number=(EditText) findViewById(R.id.input);
        Button bt_callphone=(Button)findViewById(R.id.smbin);

        //设置按钮的点击事件
        bt_callphone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {case R.id.input:
            System.out.print("");
            break;
            case R.id.smbin:
                System.out.print("");
                break;
        }

    }
}


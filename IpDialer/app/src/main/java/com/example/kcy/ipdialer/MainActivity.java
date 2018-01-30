package com.example.kcy.ipdialer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.mode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bu_save;
    EditText ed_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.print("+++++++++++++++++++++++++");
        bu_save= (Button) findViewById(R.id.bu_save);
        bu_save.setOnClickListener(this);
        ed_num= (EditText) findViewById(R.id.et_input);
    }

    @Override
    public void onClick(View v) {
        String num=ed_num.getText().toString().trim();
        //使用sp保存起来
        SharedPreferences sp= getSharedPreferences("config",0);
        //存数据
        sp.edit().putString("num",num).commit();
        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
    }
}

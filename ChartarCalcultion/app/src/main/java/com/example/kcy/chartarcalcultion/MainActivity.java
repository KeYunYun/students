package com.example.kcy.chartarcalcultion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bu_cal;
    EditText et_input;
    RadioGroup rg_gender;
    RadioButton rb_man;
    RadioButton rb_woman;
    RadioButton rb_nomannowonam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bu_cal= (Button) findViewById(R.id.bt_cal);
        et_input= (EditText) findViewById(R.id.et_input);
        rg_gender= (RadioGroup) findViewById(R.id.rg_gender);
        rb_man= (RadioButton) findViewById(R.id.rb_man);
        rb_woman= (RadioButton) findViewById(R.id.rb_woman);
        rb_nomannowonam= (RadioButton) findViewById(R.id.rb_womanandman);
        bu_cal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name =et_input.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
        }

        //判断选中的性别
        //获得选择的id
        int sex=-1;
        int radioid=  rg_gender.getCheckedRadioButtonId();
        switch (radioid){
            case R.id.rb_man:
                sex=1;
                break;
            case R.id.rb_woman:
                sex=2;
                break;
            case R.id.rb_womanandman:
                sex=3;
                break;
            default:break;
        }
        if(sex<0){
            Toast.makeText(getApplicationContext(),"请选择性别",Toast.LENGTH_SHORT).show();
        }

        //跳转页面
        Intent intent=new Intent(this,ResultActivity.class);
        //传数据
        //以键值对的方式传输
        intent.putExtra("name",name);
        intent.putExtra("sex",sex);
        startActivity(intent);
    }
}

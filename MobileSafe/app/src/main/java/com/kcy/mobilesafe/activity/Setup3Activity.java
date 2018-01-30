package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;

import static android.content.ContentValues.TAG;

/**
 * Created by kcy on 2017/5/20.
 */

public class Setup3Activity extends Activity{

    EditText input_num;
    Button select_num;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initUI();
    }

    private void initUI() {
        input_num= (EditText) findViewById(R.id.input_number);
        select_num= (Button) findViewById(R.id.bu_select_number);
        String phone=Sputils.getString(getApplicationContext(),ConstantValue.CONTACT_PHONE,"");
        input_num.setText(phone);
        select_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(),ContactListActivity.class),0);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            String phone1 = data.getStringExtra("phone");
           String phone = phone1.replace("-", "").trim();
            Log.i("phone2", "onActivityResult: "+phone);
            input_num.setText(phone);
            Sputils.putString(getApplicationContext(), ConstantValue.CONTACT_PHONE,phone);
        }


    }

    public void prePage(View view){
       startActivity(new Intent(getApplicationContext(),Setup2Activity.class));
       finish();
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }
    public void nextPage(View view){
        String phone=input_num.getText().toString();
       // String phone=Sputils.getString(getApplicationContext(),ConstantValue.CONTACT_PHONE,"");
        if (!TextUtils.isEmpty(phone)) {
            startActivity(new Intent(getApplicationContext(),Setup4Activity.class));
            Sputils.putString(getApplicationContext(), ConstantValue.CONTACT_PHONE,phone);
            finish();
            overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
        }else{
            Toast.makeText(getApplicationContext(),"请添加安全号码",Toast.LENGTH_SHORT).show();
        }

    }

}

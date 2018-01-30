package com.example.kcy.smsexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by kcy on 2017/4/12.
 */

public class ModeActivity extends Activity {
    ListView lv_mod;
    String[] sms={"我开会","我在吃饭","我在打牌"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mode);
        lv_mod= (ListView) findViewById(R.id.lv_mode);
        ArrayList<String> list=new ArrayList<String>();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.layout_item,sms);
        lv_mod.setAdapter(adapter);
        lv_mod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String context=sms[position];
                //返回给调用者
                Intent intent =new Intent();
                intent.putExtra("smscontent",context);
                setResult(20,intent);
                finish();
            }
        });
    }
}

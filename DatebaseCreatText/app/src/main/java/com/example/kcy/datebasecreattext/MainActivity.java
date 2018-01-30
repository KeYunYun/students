package com.example.kcy.datebasecreattext;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lv_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mysqlite mysqlite=new Mysqlite(this);
        SQLiteDatabase db=mysqlite.getReadableDatabase();
        findViewById(R.id.bu_add).setOnClickListener(this);
        findViewById(R.id.bt_del).setOnClickListener(this);
        findViewById(R.id.bt_select).setOnClickListener(this);
        findViewById(R.id.bt_upate).setOnClickListener(this);
       lv_list= (ListView) findViewById(R.id.lv_database);
    }

    @Override
    public void onClick(View v) {
        InfoDom infoDom =new InfoDom(this);
        switch (v.getId()){
            case R.id.bu_add:
                InfoBean bean=new InfoBean();
                bean.name="张三";
                bean.phone="123456789";
                infoDom.add(bean);
                InfoBean bean1 =new InfoBean();
                bean1.name="李四";
                bean1.phone="147852369";
                infoDom.add(bean1);

                break;
            case R.id.bt_del:
                infoDom.del("李四");
                break;
            case R.id.bt_select:
                ArrayList<InfoBean> arrayList = infoDom.select("张三");
                QueryAdater queryAdater=new QueryAdater(this,arrayList);
                lv_list.setAdapter(queryAdater);

                break;
            case R.id.bt_upate:
                InfoBean bean2=new InfoBean();
                bean2.name="张三";
                bean2.phone="987654321";
                infoDom.update(bean2);
                break;
            default:break;
        }
    }
}

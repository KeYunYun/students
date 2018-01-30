package com.kcy.mobilesafe.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.db.dao.BlackNumberDao;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Md5Util;
import com.kcy.mobilesafe.util.Sputils;

/**
 * Created by kcy on 2017/4/24.
 */

public class HomeActivity extends Activity {
    GridView gv_home;
    String[] mTitestr;
    int[] mIconstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        inttUI();
        //初始化数据
        initData();
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.PROCESS_OUTGOING_CALLS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.PROCESS_OUTGOING_CALLS},3);

        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},4);
        }
    }

    private void initData() {
        //准备数据文字，图片
        mTitestr= new String[]{
          "手机防盗","通信卫士","软件管理"
                ,"进程管理","流量统计","手机杀毒"
                ,"缓冲清理","高级工具","设置中心"
        };
         mIconstr=new int[]{
                R.drawable.home_safe,R.drawable.home_callmsgsafe,R.drawable.home_apps,
                R.drawable.home_taskmanager,R.drawable.home_netmanager,R.drawable.home_trojan,
                R.drawable.home_sysoptimize,R.drawable.home_tools,R.drawable.home_settings
        };
        //设置数据设备器
        gv_home.setAdapter(new MyAdapter());
        //注册点击事件
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showDialog();
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(),BlackNumberActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(),AppManagerActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(),ProssessManagerActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(),TrafficActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getApplicationContext(),AnitVirusActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getApplicationContext(),CacheClearActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getApplicationContext(),AToolActivity.class));
                        break;
                    case 8:
                        Intent intent =new Intent(getApplicationContext(),SettingActivity.class);
                        startActivity(intent);
                        break;
                    default:break;
                }
            }
        });
    }

    private void showDialog() {
        String psw= Sputils.getString(this, ConstantValue.MOBITLE_SAFE_PSD,"");
        if(TextUtils.isEmpty(psw)){
            showSetPsdDialog();
        }else{
            showConfirmPsdDialog();
        }
    }

    private void showConfirmPsdDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog dialog=builder.create();
        //view是由自己编写的xml转换为view对象
        final View view =View.inflate(this,R.layout.dialog_confirm_psd,null);
        dialog.setView(view);
        dialog.show();
        Button bt_submit= (Button) view.findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText et_confirm= (EditText)view.findViewById(R.id.et_confirm);
                String psd=Sputils.getString(getApplicationContext(),ConstantValue.MOBITLE_SAFE_PSD,"");
                String confirmPsd=et_confirm.getText().toString().trim();
                if (!TextUtils.isEmpty(psd)&&!TextUtils.isEmpty(confirmPsd)){

                    if(psd.equals(Md5Util.encoder(confirmPsd))){
                       Intent intent= new Intent(getApplicationContext(),SetupOverActivity.class);
                      startActivity(intent);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(),"确认密码不正确",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button bt_cancel= (Button) view.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void showSetPsdDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog dialog=builder.create();
        //view是由自己编写的xml转换为view对象
        final View view =View.inflate(this,R.layout.dialog_set_psd,null);
        dialog.setView(view);
        dialog.show();
        Button bt_submit= (Button) view.findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_set_psd= (EditText) view.findViewById(R.id.et_set_psd);
                EditText et_confirm= (EditText)view.findViewById(R.id.et_confirm_psd);
                String psd=et_set_psd.getText().toString().trim();
                String confirmPsd=et_confirm.getText().toString().trim();
                if (!TextUtils.isEmpty(psd)&&!TextUtils.isEmpty(confirmPsd)){
                    if(psd.equals(confirmPsd)){
                        Intent intent= new Intent(getApplicationContext(),SetupOverActivity.class);
                        startActivity(intent);
                        Sputils.putString(getApplicationContext(),ConstantValue.MOBITLE_SAFE_PSD, Md5Util.encoder(psd));
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(),"确认密码不正确",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button bt_cancel= (Button) view.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void inttUI() {
         gv_home= (GridView) findViewById(R.id.gv_home);
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            //条目的总数
            return mTitestr.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitestr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view =View.inflate(getApplicationContext(),R.layout.gridview_item,null);
            TextView tv_tite= (TextView) view.findViewById(R.id.tv_tite);
            ImageView im_icon= (ImageView) view.findViewById(R.id.im_icon);
            tv_tite.setText(mTitestr[position]);
            im_icon.setBackgroundResource(mIconstr[position]);
            return view;
        }
    }
}

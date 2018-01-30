package com.example.kcy.revier_boradtext;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ScreenRecesiver screenRecesiver;
    Button bt_1;
    Button bt_2;
    Button bt_3;
    Button bt_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         screenRecesiver =new ScreenRecesiver();
       //创建intent-filter对象
        IntentFilter filter =new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        this.registerReceiver(screenRecesiver,filter);
        bt_1= (Button) findViewById(R.id.button);
        bt_1.setOnClickListener(this);
        bt_2= (Button) findViewById(R.id.button2);
        bt_2.setOnClickListener(this);
        bt_3= (Button) findViewById(R.id.button3);
        bt_3.setOnClickListener(this);
        bt_4= (Button) findViewById(R.id.button4);
        bt_4.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {

        //当activity销毁时取消注册
        unregisterReceiver(screenRecesiver);
        System.out.println("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:

                button1();
                break;
            case R.id.button2:

                button2();
                break;
            case R.id.button3:

                button3();
                break;
            case R.id.button4:

                button4();
                break;
        }
    }

    private void button4() {
        final ProgressDialog progre=new ProgressDialog(this);
        progre.setTitle("正在加载中");

        //设置样式
        progre.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progre.setMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<100;i++){
                    progre.setProgress(i);
                    SystemClock.sleep(100);
                }
                progre.dismiss();
            }
        }).start();
        progre.show();
    }

    private void button3() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("选择你喜欢的水果");
        final String[] fruit={"苹果","香蕉","葡萄","大枣","小锅米线"};
        final boolean[] checkItems={true,false,false,false,false};
        builder.setMultiChoiceItems(fruit, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });
        builder.setPositiveButton("确定选择", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuffer sb=new StringBuffer();
                for(int i=0;i<fruit.length;i++){
                    if(checkItems[i]){
                        sb.append(fruit[i]+"");
                    }
                }
                Toast.makeText(getApplicationContext(),sb,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void button2() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("选课");
        final String items[]={"android","ios","c++","c#"};
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //选择选择的结果
                String calss=items[which];
                Toast.makeText(getApplicationContext(),"你选的为"+calss,Toast.LENGTH_SHORT).show();
                //关闭对话框
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void button1() {

        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("没有网络");
        System.out.println(7894);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("点击了确定按钮");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("你点击了取消");
            }
        });
        builder.show();
    }
}

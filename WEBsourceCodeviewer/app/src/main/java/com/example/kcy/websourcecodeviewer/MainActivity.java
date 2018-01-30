package com.example.kcy.websourcecodeviewer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_text;
    TextView tv_look;
    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext=this;

        //1.找到相应控件
          Button bt_look= (Button) findViewById(R.id.bt_look);
         tv_look= (TextView) findViewById(R.id.tv_look);
         et_text= (EditText) findViewById(R.id.et_text);
        //2设置点击事件
        bt_look.setOnClickListener(this);
        //3.获取URL地址
    }
    //1.在主线程中创建一个Handler对象
    private Handler handler =new Handler(){
        //2.重写handler的handle人message方法，用来接收子线程中的消息
        public void handleMessage(android.os.Message msg){
            //接收子线程发来的数据，并处理
           String result=(String) msg.obj;
            tv_look.setText(result);
        }
    };
    @Override
    public void onClick(View v) {


            //3.获取URL地址
            final String url_str = et_text.getText().toString().trim();
            if(TextUtils.isEmpty(url_str)){
                Toast.makeText(mcontext, "url不能为空", Toast.LENGTH_SHORT).show();
                return ;
            }
        System.out.println("123面"+Thread.currentThread());

            //创建一个子线程做网络请求
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //4.请求url地址
                        //1.创建一个url对象
                        URL url = new URL(url_str);
                        //2.获取一个UrlConnection对象
                        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                        System.out.println(url);
                        System.out.println(connection);

                        //3.为UrlConnection对象设置一些请求的参数,请求方式，连接的超时时间
                        connection.setRequestMethod("GET");//设置请求方式
                        connection.setConnectTimeout(1000*10);//设置超时时间
                        connection.setDoInput(true);
                        connection.setDoOutput(true);

                        System.out.println(132);
                        int code = connection.getResponseCode();
                        System.out.println(code);
                        System.out.println(321);
                        if(code == 200){
                            //5.获取有效数据，并将获取的流数据解析成String
                            InputStream inputStream = connection.getInputStream();
                            String result = StreamUtiles.streamToString(inputStream);
                            System.out.println("里面"+Thread.currentThread());

                            //Handler 3.子线程中创建一个Message对象，为了携带子线程中获得的数据给主线程
                            Message message =new Message();
                            //将获得的数据封装到message中
                            message.obj=result;
                            //4.使用Handler对象将message发送给主线程
                            handler.sendMessage(message);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

                 //2.获取一个URL对象
                //3.为URLConnection设置一些请求参数，请求方式
                //4.在获取url请求前判断响应码
                //200:成功   300：跳转或重定向
                // 206：访问部分数据成功
                //400:错误   500：服务器异常


    }
}

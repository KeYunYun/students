package com.example.kcy.fileupload;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    Button bu_upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      bu_upload=(Button)  findViewById(R.id.bu_upload);
        bu_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed_file= (EditText) findViewById(R.id.ed_file);
              //获取文件的输入地址
                String filepath=ed_file.getText().toString().trim();
                //使用开源Util做上传
                AsyncHttpClient asyncHttpClient =new AsyncHttpClient();
                RequestParams params =new RequestParams();
                try {
                    params.put("filename",new File(filepath));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //url：请求服务器的url
                asyncHttpClient.post("http://10.1.110.12:8080/itheima74/servlet/UploaderServlet", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }


                })
            }
        });
    }



}

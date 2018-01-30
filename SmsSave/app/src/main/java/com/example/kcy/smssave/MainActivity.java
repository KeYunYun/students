package com.example.kcy.smssave;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        System.out.println(permissionCheck);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_SMS)){

            }else{

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                System.out.println("里面"+ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS));
                 }
        }
        if( ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS2);
                System.out.println("sd里面"+ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE));
            }
        }
        System.out.println("sd外面"+ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE));
        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取XmlSerialer实例
                XmlSerializer serializer = Xml.newSerializer();
                //设置序列化参数
                File file =new File(Environment.getExternalStorageDirectory().getPath(),"smsbackup.xml");
               try{
                   FileOutputStream fos=new FileOutputStream(file);
                   serializer.setOutput(fos,"utf-8");
                   //写xml文档的开头
                   serializer.startDocument("utf-8",true);
                   //写xml的根节点
                   serializer.startTag(null,"smss");

                   // 构造 uri
                   Uri uri = Uri.parse("content://sms/");
                   Cursor cursor =getContentResolver().query(uri,new String[]{"address","date","body"},null,null,null);
                   if(cursor!=null&&cursor.getCount()>0) {
                       while (cursor.moveToNext()) {

                           String address = cursor.getString(0);
                           String date = cursor.getString(1);
                           String body = cursor.getString(2);
                           System.out.println(address + date + body);
                           //写sms节点

                           //[7]写sms节点
                           serializer.startTag(null, "sms");
                           //[8]写address节点
                           serializer.startTag(null, "address");
                           serializer.text(address);
                           serializer.endTag(null, "address");

                           //[9]写date节点
                           serializer.startTag(null, "date");
                           serializer.text(date);
                           serializer.endTag(null, "date");
                           //[10]写body节点
                           serializer.startTag(null, "body");
                           serializer.text(body);
                           serializer.endTag(null, "body");

                           serializer.endTag(null, "sms");

                       }
                   }
                   serializer.endTag(null,"smss");
               }catch (Exception e){
                   e.printStackTrace();
               }


            }
        });
    }
}

package com.kcy.mobilesafe.engine;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kcy on 2017/6/1.
 */

public class SmsBackUp {
    //传递一个实现接口的对象
    public static void bachUp(Context context, String path, CallBack  dialog){
       int index=1;
        FileOutputStream fos=null;
        //1 获得备份短信写入的文件
        File file =new File(path);
        //获得内容解析器
       Cursor cursor= context.getContentResolver().query(Uri.parse("content://sms/"),
                new String[]{"address","date","type","body"},null,null,null);
        //文件相应的输出流
        try{

            fos = new FileOutputStream(file);
            //序列化数据库中读取的数据，放入xml中
            XmlSerializer newSerialier= Xml.newSerializer();
            //给xml做相应的设置
            newSerialier.setOutput(fos,"utf-8");
            //备份短信总数的制定
            if(dialog!=null){
                dialog.setMax(cursor.getCount());
            }

            //DTD
            newSerialier.startDocument("utf-8",true);


            newSerialier.startTag(null,"smss");
                //读取数据库中的内容写入xml中
                while (cursor.moveToNext()){
                    newSerialier.startTag(null,"sms");
                     newSerialier.startTag(null,"address");
                        newSerialier.text(cursor.getString(0));
                     newSerialier.endTag(null,"address");

                    newSerialier.startTag(null,"date");
                    newSerialier.text(cursor.getString(1));
                    newSerialier.endTag(null,"date");

                    newSerialier.startTag(null,"type");
                    newSerialier.text(cursor.getString(2));
                    newSerialier.endTag(null,"type");

                    newSerialier.startTag(null,"body");
                    newSerialier.text(cursor.getString(3));
                    newSerialier.endTag(null,"body");
                    newSerialier.endTag(null,"sms");
                    //没循环一次就让精度条叠加

                    if(dialog!=null){
                        dialog.setProgress(index++);
                    }

                }
            newSerialier.endTag(null,"smss");
            newSerialier.endDocument();
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null&&fos!=null){
                cursor.close();
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public interface  CallBack{
        //定义一个接口
        //2.定义未实现的业务逻辑
        public void setMax(int max);
        public void setProgress(int index);
        //传递一个实现接口的对象

    }
}

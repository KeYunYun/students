package com.example.kcy.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kcy on 2017/3/29.
 */

class UseNamePwd {
    //将密码保存在本地
    public static boolean getNamePwa(Context context, String uername, String pwd) {

       try{
         /*  String useinfomation= uername+"###"+pwd;
          // String path="/data/data/com.example.kcy.login/";
          // String path="/mnt/sdcard/";//sdcard
           String path = Environment.getExternalStorageDirectory().getPath();
           File file=new File(path,"userInfo");
           FileOutputStream fos=new FileOutputStream(file);
           fos.write(useinfomation.getBytes());
           fos.close();*/
         // 1.通过一个context创建sharedprefercence对象
           //name:文件的名称  mode： 文件的操作模式
           SharedPreferences sharedPreferences= context.getSharedPreferences("useInfo.text",Context.MODE_PRIVATE);
           //2.同sharePreference获得editor对象
           SharedPreferences.Editor editor =sharedPreferences.edit();
           //3.向editor里面添加数据
           editor.putString("username",uername);
           editor.putString("pwd",pwd);
           //提交editor对象
           editor.commit();
            return true;
       }catch (Exception e){
           e.printStackTrace();
       }

        return false;
    }
    public static Map<String,String> gerUserInfor(Context context){
        try{
        /* //   String path="/data/data/com.example.kcy.login/";
          //  String path="/mnt/sdcard/";
            String path=Environment.getExternalStorageDirectory().getPath();
            File file=new File(path,"userInfo");
            FileInputStream fis=new FileInputStream(file);
            BufferedReader bread=new BufferedReader(new InputStreamReader(fis));

            String readLine=bread.readLine();
            String[] split=readLine.split("###");
            HashMap<String,String> hashMap=new HashMap<String,String>();
            hashMap.put("username",split[0]);
            hashMap.put("password",split[1]);
            bread.close();
            fis.close();
            return hashMap;*/
            //1.通过context创建sharedprefence对象
            SharedPreferences sharedPreferences=context.getSharedPreferences("useInfo.text",Context.MODE_PRIVATE);
            //2.通过sharedprefences获得数据
            //defvalue:表示没有获得值时输出的值
            String username=sharedPreferences.getString("username","");
            String pwd=sharedPreferences.getString("pwd","");

            HashMap<String,String> hashMap=new HashMap<String, String>();
            hashMap.put("usename",username);
            hashMap.put("password",pwd);
            return hashMap;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

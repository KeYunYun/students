package com.example.kcy.newstext;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kcy on 2017/4/5.
 */

public class NesUtils {
    private static String URL_Path="http://10.1.110.12:8080//itheima74/servlet/GetNewsServlet";
    public static ArrayList<NewBean> getAllNewsWeb(Context context){
        ArrayList<NewBean> list=new ArrayList<NewBean>();
    /*    for(int i=0;i<20;i++) {
            NewBean newBean = new NewBean();
            newBean.title = "谢霆锋经纪人：偷拍系侵犯行为!";
            newBean.des = "称谢霆锋隐私受到侵犯，请追究责任";
            newBean.new_url = "http://www.sina.cn";
            newBean.icon = ContextCompat.getDrawable(context, R.drawable.tu);
            list.add(newBean);
            NewBean newBean2 = new NewBean();
            newBean2.title = "谢霆锋经纪人：你好啊";
            newBean2.des = "称谢霆锋隐私受到侵犯，请追究责任";
            newBean2.new_url = "http://www.sina.cn";
            newBean2.icon = ContextCompat.getDrawable(context, R.drawable.tu2);
            list.add(newBean2);
            NewBean newBean3 = new NewBean();
            newBean3.title = "谢霆锋经纪人：哈哈哈哈哈";
            newBean3.des = "称谢霆锋隐私受到侵犯，请追究责任";
            newBean3.new_url = "http://www.baidu.com";
            newBean3.icon = ContextCompat.getDrawable(context, R.drawable.tu3);
            list.add(newBean3);
        }*/
        try{
            URL url =new URL(URL_Path);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10*1000);
            System.out.println(url);

            int code =connection.getResponseCode();
            System.out.println(code);
            if(code==200){
                InputStream inputStream=connection.getInputStream();
                String result=StreamUtiles.streamTostring(inputStream);
                System.out.println(result);
                //解析josn
               JSONObject jsonObject= new JSONObject(result);//将一个字符穿封装为JSON
               JSONArray jsonArray= jsonObject.getJSONArray("newss");
                //获取json中的newss的Jsonarry对象
                //遍历jsonarry

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject news_json=jsonArray.getJSONObject(i);

                    NewBean newBean=new NewBean();

                    //获取一天新闻的json
                    newBean.icon_url=news_json.getString("icon_url");
                    newBean.id =news_json.getInt("id");
                    newBean.type=news_json.getInt("type");

                    newBean.comment=news_json.getInt("comment");

                    newBean.time=news_json.getString("time");

                    newBean.des=news_json.getString("des");

                    newBean.title=news_json.getString("title");

                    newBean.new_url=news_json.getString("news_url");




                    list.add(newBean);
                }
                new NewsDao(context).delete();
                new NewsDao(context).saveNews(list);
            }

        }catch (Exception e){

        }


        return list;
    }
    public static ArrayList<NewBean> getAllNewsDB(Context context){
        return  new NewsDao(context).getNews();
    }
}




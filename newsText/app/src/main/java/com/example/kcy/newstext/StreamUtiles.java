package com.example.kcy.newstext;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by kcy on 2017/4/8.
 */

public class StreamUtiles {

    public static String streamTostring(InputStream in){
        String result="";
        try{
            ByteArrayOutputStream out =new ByteArrayOutputStream();
            byte[] buffer =new byte[1024];
            int length=0;
            while((length=in.read(buffer))!=-1){
                out.write(buffer,0,length);
                out.flush();
            }
            result=out.toString();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        return result;
    }
}

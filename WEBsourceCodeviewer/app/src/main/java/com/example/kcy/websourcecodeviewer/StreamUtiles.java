package com.example.kcy.websourcecodeviewer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kcy on 2017/4/6.
 */

public class StreamUtiles {
    public static  String streamToString(InputStream in){
        String result ="";

        try{
            //创建一个字节数组写入流
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length = 0;
            while (  (length =  in.read(buffer)) !=-1) {
                out.write(buffer, 0, length);
                out.flush();
            }

            result = out.toString();//将字节流转换成string

            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
}

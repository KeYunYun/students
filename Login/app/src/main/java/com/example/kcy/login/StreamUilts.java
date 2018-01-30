package com.example.kcy.login;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by kcy on 2017/4/8.
 */

public class StreamUilts {
    public static String getInputToStream(InputStream in){
        String resurt="";
      try {
          byte[] buffer =new byte[1024];
          int length=0;
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          while (  (length =  in.read(buffer)) !=-1) {
              out.write(buffer, 0, length);
              out.flush();
          }

          resurt = new String(out.toByteArray(),"utf-8");

//			result = out.toString();//将字节流转换成string

          out.close();

      }catch (Exception e){
          e.printStackTrace();
      }
        return resurt;
    }
}

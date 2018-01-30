package com.kcy.mobilesafe.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kcy on 2017/4/24.
 */

public class StrramUtil {

    public static String streamToStrinf(InputStream is) throws IOException {
        //流装换为字节，将读取的内容存储在缓冲中
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        //读取操作，读到没有为止
        byte[] buffer=new byte[1024];
        int temp=-1;
        try{
            while ((temp=is.read(buffer))!=-1){
                bos.write(buffer,0,temp);
            }
            return bos.toString();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bos.close();
            is.close();
        }
        return null;
    }
}

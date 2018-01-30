package com.kcy.mobilesafe.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kcy on 2017/4/25.
 */

public class Sputils {
    private static SharedPreferences sp;
    public static void putBoolean(Context context,String key,boolean value){

        if(sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
            sp.edit().putBoolean(key,value).commit();

    }
    public static boolean getBoolean(Context context,String key,boolean defvalue){

        if(sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
         return  sp.getBoolean(key,defvalue);

    }
    public static void putString(Context context,String key,String value){

        if(sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();

    }
    public static String getString(Context context,String key,String defvalue){

        if(sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return  sp.getString(key,defvalue);

    }

    public static void remove(Context context,String key) {

        if(sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }
    public static void putInt(Context context,String key,int value){

        if(sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key,value).commit();

    }
    public static int getInt(Context context,String key,int defvalue){

        if(sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return  sp.getInt(key,defvalue);

    }
}

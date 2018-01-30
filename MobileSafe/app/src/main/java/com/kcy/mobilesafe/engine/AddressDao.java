package com.kcy.mobilesafe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by kcy on 2017/5/23.
 */

public class AddressDao {
    public  static String path="/data/data/com.kcy.mobilesafe/databases/address.db";
    public static String getAddress(String phone){
        String regularExpress="^1[3-8]\\d{9}";
        String address="未知号码";
        if(phone.matches(regularExpress)){
            phone=phone.substring(0,7);
            SQLiteDatabase sqLiteDatabase= SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY) ;
            Cursor cursor= sqLiteDatabase.query("data1",new String[]{"outkey"},"id=?",new String[]{phone},null,null,null);
            if(cursor.moveToNext()){
                String outkey=  cursor.getString(0);
                Log.i("outkey", "getAddress: "+outkey);
                Cursor index= sqLiteDatabase.query("data2",new String[]{"location"},"id=?",new String[]{outkey},null,null,null);
                if(index.moveToNext()){

                    address = index.getString(0);
                    Log.i("outkey1", "getAddress: "+address);
                }

            }else{
                address="未知号码";
            }
        }else{
            int length=phone.length();
            switch (length){
                case 3:
                  address="报警号码";
                    break;

                    case 4:
                        address="模拟器";
                        break;

                        case 5:
                            address="服务号码";
                            break;

                            case 7:
                                address="本地号码";
                                break;

                case 8:
                    address="本地号码";
                    break;
            }
        }

        return address;
    }
}

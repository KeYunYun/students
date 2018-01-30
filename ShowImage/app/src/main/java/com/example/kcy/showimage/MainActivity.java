package com.example.kcy.showimage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;
    Button bt_play;
    ImageView iv;
    int screenwith;
    int screenheight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        System.out.println(permissionCheck);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                System.out.println("里面"+ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE));
            }
        }

        iv= (ImageView) findViewById(R.id.iv_show);
        bt_play= (Button) findViewById(R.id.bt_play);
        //实例化windowManager 获得守家的宽高分辨率等等
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            Point point =new Point();
            wm.getDefaultDisplay().getSize(point);
             screenwith=point.x;
            screenheight=point.y;
        System.out.println("width"+screenwith+"height"+screenheight  );
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建工厂的配置参数
                BitmapFactory.Options options=new BitmapFactory.Options();
                //返回一个null ，返回图片的一些信息，高宽
                options.inJustDecodeBounds=true;
                //获得图片宽高
                int imagewidth=options.outWidth;
                int imageheight=options.outHeight;
                System.out.println("图片的"+imageheight+"宽"+imagewidth);
                //计算缩放比
                int scale=1;
                int scalex=imagewidth/screenwith;
                int scaley=imageheight/screenheight;
                if(scalex>=scaley&&scaley>scale){
                    scale=scaley;
                }
                if(scaley>scalex&&scaley>scale){
                    scale=scaley;
                }
                System.out.println("缩放比"+scale);
                //按照缩放比显示图片
                options.inSampleSize=scale;
                //开始解析位图;
                options.inJustDecodeBounds=false;
                //把 dog.jpg 转换为bitmap
                Bitmap bitmap= BitmapFactory.decodeFile("/mnt/sdcard/dog.jpg",options);
                iv.setImageBitmap(bitmap);
            }
        });
    }
}

package com.example.kcy.drawingbroad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_show;
    Bitmap bitmap;
    Bitmap copybitmap;
    Canvas canvas;
    Paint paint;
    Button bt_red;
    Button bt_bold;
    Button bt_save;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_show= (ImageView) findViewById(R.id.iv_show);
         bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        //创建模板
         copybitmap=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        //创建画布
         canvas=new Canvas(copybitmap);
       //创建画笔
         paint=new Paint();
        //开始画画
        canvas.drawBitmap(bitmap,new Matrix(),paint);
        //给iv设置触摸事件
        iv_show.setOnTouchListener(new View.OnTouchListener() {
            int startx=0;
            int starty=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               //获得触摸的事件类型
                int action =event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN://按下
                        //获取按下的坐标
                         startx= (int) event.getX();
                       starty= (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE://移动
                        //获取停止的坐标
                        int stopx= (int) event.getX();
                        int stopy= (int) event.getY();

                        canvas.drawLine(startx,starty,stopx,stopy,paint);
                        startx=stopx;
                        starty=stopy;
                        iv_show.setImageBitmap(copybitmap);
                        break;
                    case MotionEvent.ACTION_UP://没按
                        break;
                }
                return true;
                //flase只能执行一个事件
                //true可以执行多个事件
            }
        });

        iv_show.setImageBitmap(copybitmap);
        
        
        bt_red= (Button) findViewById(R.id.bt_red);
        bt_red.setOnClickListener(this);
        bt_bold= (Button) findViewById(R.id.bt_bold);
        bt_bold.setOnClickListener(this);
        bt_save= (Button) findViewById(R.id.bt_save);
        bt_save.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_red:
                onclickRed();
                break;
            case R.id.bt_bold:
                onclickBold();
                break;
            case R.id.bt_save:
                onclickSave();
                break;
        }
    }

    private void onclickSave() {
        //format 保存图片的格式
        //quality 保存的质量
        //file 保存的位置

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
        try{
        File file=new File(Environment.getExternalStorageDirectory().getPath(),"dazuo.png");
        FileOutputStream fos=new FileOutputStream(file);
        copybitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();
            System.out.println("保存成功");
            //发送一条广播给图库
            Intent intent =new Intent();
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            sendBroadcast(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void onclickBold() {
        paint.setStrokeWidth(10);
    }

    private void onclickRed() {
        paint.setColor(Color.RED);
    }
}

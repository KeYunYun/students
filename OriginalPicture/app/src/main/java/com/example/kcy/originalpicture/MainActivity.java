package com.example.kcy.originalpicture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView image1;
    ImageView image2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image1= (ImageView) findViewById(R.id.iv_im1);
        image2= (ImageView) findViewById(R.id.im_im2);
        //把.png转换为bitmap
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.tomcat);
        image1.setImageBitmap(bitmap);
        //拷贝原图
        //创建一个一样的模板
        Bitmap copybitmap= Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        //创建一个画布canvas 以copybitmap为模板创建画布
        Canvas canvas =new Canvas(copybitmap);
        //创建一个画笔paint;
        Paint paint =new Paint();
        //处理图像的api
        Matrix matrix=new Matrix();
        //对图片进行旋转
        matrix.setRotate(20,copybitmap.getWidth(),copybitmap.getHeight());
        //开始画画，srcBitmap参考原图去画
        canvas.drawBitmap(bitmap,matrix,paint);
        //把画布显示到控件上面
        image2.setImageBitmap(copybitmap);
    }
}

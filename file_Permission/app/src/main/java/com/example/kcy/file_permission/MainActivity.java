package com.example.kcy.file_permission;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //文件的操作的四种
        //创建在私有目录下，/packname/file/
        try{
            this.openFileOutput("private.text", Context.MODE_PRIVATE).write("private".getBytes());
            //Context.MODE_PRIVATE：为默认操作模式，代表该文件是私有数据，只能被应用本身访问，
            // 在该模式下，写入的内容会覆盖原文件的内容，
            this.openFileOutput("append.text",Context.MODE_APPEND).write("append".getBytes());
            //模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

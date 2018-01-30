package com.example.kcy.filedownload;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   // Button bt_download;
    EditText et_iput;
    private Context mcontext;
    private  int threadcode=3;
    private  int threadsize=0;
    private Map<Integer,ProgressBar> map;
   private  static String urlPath="http://10.1.110.12:8080/itheima74/feiq.exe";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       findViewById(R.id.bt_download).setOnClickListener(this);
        et_iput= (EditText) findViewById(R.id.et_input);
        mcontext=this;
    }

    @Override
    public void onClick(View v) {
       String threadcount_str=et_iput.getText().toString().trim();
        threadcode=Integer.parseInt(threadcount_str);
        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.progress_layout);
        //清空子控件
        linearLayout.removeAllViews();
        //根据线程数添加一定数量的progressBar
        for(int i=0;i<threadcode;i++){
            //添加子控件
            ProgressBar progress= (ProgressBar) View.inflate(mcontext,R.layout.progress_layout,null);
            linearLayout.addView(progress);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                download();
            }
        }).start();

    }

    public void download(){


        try {
            URL url =new URL(urlPath);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10*1000);
            int code=connection.getResponseCode();
            System.out.println(code);
            if(code==200){
                //获得资源的大小
                int filelength=connection.getContentLength();
                //在文件中创建一个大小一样的文件
                RandomAccessFile randmfile=	new RandomAccessFile(new File(getFileUrl()+"feiq.exe"), "rw");
                randmfile.setLength(filelength);
                //分配每个线程下载的开始位置和结束位置
                threadsize=filelength/threadcode;
                for(int i=0;i<threadcode;i++){

                    int startIndex=-1;
                    int endTndex=-1;
                    startIndex=i*threadcode;
                    endTndex=(i+1)*threadcode-1;
                    if(i==threadcode-1){
                        endTndex=filelength-1;
                    }
                    System.out.println(startIndex);
                    new Download(i, startIndex, endTndex).start();
                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public  class Download extends Thread{
        private int threadid;
        private int startindex;
        private int endindex;
        private int lastpost;
        private int runthreadcount=0;
       private int currentThreadtotal;

        public Download(int threadid,int startindex,int endindex){
            this.threadid=threadid;
            this.startindex=startindex;
            this.endindex=endindex;
            System.out.println("线程kai"+threadid);
            this.currentThreadtotal=endindex-startindex;

        }
        @Override
        public void run() {
            //分段请求网络连接，分段保存
            ProgressBar progressBar =map.get(threadid);
            try {
                synchronized (Download.class) {
                    runthreadcount++;
                }

                URL url =new URL(urlPath);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10*1000);

                //设置分段下载的头信息 Ragne:做分段数据请求用的
                //读取上次下载结束的位置
                File file2=new File(getFilepath()+threadid+".txt");
                if(file2.exists()){
                    BufferedReader bufferread=	new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
                    String lastPostion_str =bufferread.readLine();
                    lastpost=Integer.parseInt(lastPostion_str);
                    connection.setRequestProperty("Range", "bytes:"+startindex+"-"+endindex);
                }else{
                    connection.setRequestProperty("Range", "bytes:"+startindex+"-"+endindex);
                }
                int code=connection.getResponseCode();
                System.out.println(code);
                if(code==206){
                    //将请求成功的写入文件
                    InputStream in=connection.getInputStream();
                    RandomAccessFile raf=new RandomAccessFile(new File("feiq.exe"), "rw");
                    //设置重什么位置开始写
                    raf.seek(lastpost);
                    //写入流文件
                    byte[] buffer=new byte[1024];
                    int length=-1;
                    int total=0;
                    while((length=in.read(buffer))!=-1){
                        progressBar.setMax(currentThreadtotal);

                        raf.write(buffer,0,length);
                        total=total+length;
                        int currentThreadpostion=lastpost+total;
                        //计算出当前位置
                        File file=new File(getFilepath()+threadid+".txt");
                        RandomAccessFile accessfile=new RandomAccessFile(file, "rwd");
                        accessfile.write(String.valueOf(currentThreadpostion).getBytes());
                        accessfile.close();
                        int currentindex=endindex-currentThreadpostion;
                        progressBar.setProgress(currentindex);
                    }
                    in.close();
                    raf.close();

                    System.out.println("线程下载网络"+threadid);
                    synchronized (Download.class) {
                        runthreadcount--;
                        if(runthreadcount==0){
                            for(int i=0;i<threadcode ;i++){
                                File file=	new File(getFilepath()+i+".text");
                                file.delete();
                            }
                        }
                    }

                }

            } catch (Exception e) {
                // TODO: handle exception
            }

            super.run();
        }
    }

    public static String getFileUrl(){
        return Environment.getExternalStorageDirectory() +"/"+ urlPath.substring(urlPath.indexOf("/"));
    }
    public  String getFilepath(){
        return Environment.getExternalStorageDirectory() +"/";
    }

}

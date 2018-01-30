package com.kcy.mobilesafe.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;
import com.kcy.mobilesafe.util.StrramUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.factory.BitmapFactory;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {
    private static final int UPDATE_VERSION = 100;
    private static final int ENTER_HOME =101 ;
    private static final int ERROR =102 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;
    String  mVersionDes;
    String mVersionUrl;
    TextView tv_wersion;
    RelativeLayout rl_splash;
    int mLoadmVersioncode=0;
    int versionCode=0;
    private Handler mHander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_VERSION:
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    //进入主界面
                    enterHome();
                    break;
                case ERROR:
                    //进入主界面
                    enterHome();
                    Toast.makeText(getApplicationContext(),"网络出错",Toast.LENGTH_SHORT).show();
                    break;
                default:break;
            }
        }
    };

    private void showUpdateDialog() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("版本跟新");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("立即跟新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载apk;
                downloadApk();
            }
        });
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消对话框，进入主界面
                enterHome();
            }
        });
        builder.show();
    }

    private void downloadApk() {
        //链接地址，防止apk的地址



        //判断sd卡是否可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //获得sd路径mobilesafe69.apk
           String path= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"mobilesafe69.apk";
            System.out.println(path);
            //发送请求，获得apk,并放在指定路径
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.download("http://10.1.110.12:8080/mobilesafe69.apk", path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    //下载成功
                    File file =responseInfo.result;
                    System.out.println("下载成功");
                    //安装apk
                    installApk(file);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    //下载失败
                    System.out.println("下载失败");
                }

                @Override
                public void onStart() {
                    super.onStart();
                    System.out.println("刚刚开始下载");
                }
                //下载过程中的方法
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    System.out.println("下载中");

                }
            });
        }

    }
    //安装zpk文件
    private void installApk(File file) {
        //系统界面
        //系统应用界面,源码,安装apk入口
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
		/*//文件作为数据源
		intent.setData(Uri.fromFile(file));
		//设置安装的类型
		intent.setType("application/vnd.android.package-archive");*/
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
//		startActivity(intent);
        startActivityForResult(intent, 0);

    }

    /*
    进入主界面
     */
    private void enterHome() {

       Intent intent=  new Intent(this, HomeActivity.class);
        startActivity(intent);

        //开启新界面后，关闭导肮界面
       finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        System.out.println(permissionCheck);
        if(permissionCheck!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                System.out.println("里面"+ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE));
            }
        }
        //初始化UI
        initUI();
        //初始化数据
        initDate();
        initAnimation();
        initDB();
        initShortCut();

    }

    private void initShortCut() {
//1,给intent维护图标,名称
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //维护图标
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON,
                android.graphics.BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        //名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "手机卫士");
        //2,点击快捷方式后跳转到的activity
        //2.1维护开启的意图对象
        Intent shortCutIntent = new Intent("android.intent.action.HOME");
        shortCutIntent.addCategory("android.intent.category.DEFAULT");

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
        //3,发送广播
        sendBroadcast(intent);
        //4,告知sp已经生成快捷方式
    }

    private void initDB() {
        initAddressDB("address.db");
        initcommonumDB("commonnum.db");
        initantivirusDB("antivirus.db");
    }

    private void initantivirusDB(String sdb) {
        File file = new File("/data/data/com.kcy.mobilesafe/databases/antivirus.db");
        Log.i("file", "antivirus.db: "+file);
        Log.i("file", "antivirus.db: "+file.exists());
        if(file.exists()){
            Log.i("file", "存在");
        }else{
            Log.i("log:SplashActivity", "开始拷贝了。。。。");

            // 拷贝
            try {
                InputStream is = getAssets().open("antivirus.db");    // 获取数据库库文件输入流
                FileOutputStream fos = new FileOutputStream(file);  // 定义输出流
                byte[] bt = new byte[1024];
                int len = -1;
                while((len = is.read(bt)) != -1){
                    fos.write(bt, 0, len);
                }
                is.close();
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void initcommonumDB(String sdb) {
        File file = new File("/data/data/com.kcy.mobilesafe/databases/commonnum.db");
        Log.i("file", "initcommonumDB: "+file);
        Log.i("file", "initcommonumDB: "+file.exists());
        if(file.exists()){
            Log.i("file", "存在");
        }else{
            Log.i("log:SplashActivity", "开始拷贝了。。。。");

            // 拷贝
            try {
                InputStream is = getAssets().open("commonnum.db");    // 获取数据库库文件输入流
                FileOutputStream fos = new FileOutputStream(file);  // 定义输出流
                byte[] bt = new byte[1024];
                int len = -1;
                while((len = is.read(bt)) != -1){
                    fos.write(bt, 0, len);
                }
                is.close();
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void initAddressDB(String dbName) {
      //  File file=new File(getApplicationContext().getFilesDir(),"address.db");
        File file = new File("/data/data/com.kcy.mobilesafe/databases/address.db");

        if(file.exists()){
            Log.i("file", "存在");
        }else{
            Log.i("log:SplashActivity", "开始拷贝了。。。。");

            // 拷贝
            try {
                InputStream is = getAssets().open("address.db");    // 获取数据库库文件输入流
                FileOutputStream fos = new FileOutputStream(file);  // 定义输出流
                byte[] bt = new byte[1024];
                int len = -1;
                while((len = is.read(bt)) != -1){
                    fos.write(bt, 0, len);
                }
                is.close();
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void initAnimation() {
        AlphaAnimation alp=new AlphaAnimation(0,1);
        alp.setDuration(3000);
        rl_splash.startAnimation(alp);
    }

    private void initDate() {
        //获得版本名称
      tv_wersion.setText( "当前版本号为"+getVersionName());
        //检查本地号是否与服务器版本号一样
        //获得本地服务号
        mLoadmVersioncode= getVersionCode();
        //获得服务器服务号（json）
        //检查版本号
        if(Sputils.getBoolean(this, ConstantValue.OPEN_UPDATE,false)){
            checkVersion();
        }else{
            mHander.sendEmptyMessageDelayed(ENTER_HOME,4000);
        }

    }

    private void checkVersion() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message msg=Message.obtain();
                long starTime=System.currentTimeMillis();
                try {
                    //请求json的；链接地址
                   URL url= new URL("http://10.1.110.12:8080/updateapk.json");
                    //开启一个链接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置常见的请求头
                    //请求超时
                    connection.setConnectTimeout(2000);
                    //读取超时
                    connection.setReadTimeout(2000);
                    connection.setRequestMethod("GET");
                    //获取响应码
                    if(connection.getResponseCode()==200){
                        //以流的形式获取数据
                        InputStream is=connection.getInputStream();
                        //将流装换为字节
                       String json = StrramUtil.streamToStrinf(is);
                        System.out.println(json);//
                   //json解析
                        JSONObject jsonobject=new JSONObject(json);
                    mVersionDes=    jsonobject.getString("description");
                     mVersionUrl=   jsonobject.getString("download_url");
                     int versionCode=   jsonobject.getInt("version_code");
                      String versionName=  jsonobject.getString("version_name");
                        System.out.println(mLoadmVersioncode);//
                        System.out.println(mLoadmVersioncode<versionCode);//
                        if(mLoadmVersioncode<versionCode){
                            //提示更新 跳出UI
                            msg.what=UPDATE_VERSION;
                        }else{

                            msg.what=ENTER_HOME;
                        }

                    }
                } catch (Exception e) {
                    msg.what=ERROR;

                    e.printStackTrace();
                }finally {
                    //制定睡眠时间
                    //请求网络时间+制定的时间为4秒
                    //
                    long endTime=System.currentTimeMillis();
                    if(endTime-starTime<4000){
                        try {
                            Thread.sleep(4000-(endTime-starTime));
                            System.out.print(4000-(endTime-starTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHander.sendMessage(msg);
                }
            }
        }.start();
    }

    private void initUI() {
         tv_wersion= (TextView) findViewById(R.id.tv_version_name);
        rl_splash= (RelativeLayout) findViewById(R.id.rl_splash);
    }

    public String  getVersionName() {
        //包的管理者对象
        PackageManager pm=getPackageManager();
        //获得包的管理者得到版本号,0表示获得基本数据
        try{
          PackageInfo info= pm.getPackageInfo(getPackageName(),0);
           //获得版本号
            return info.versionName;
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    public int getVersionCode() {
        //包的管理者对象
        PackageManager pm=getPackageManager();
        //获得包的管理者得到版本号,0表示获得基本数据
        try{
            PackageInfo info= pm.getPackageInfo(getPackageName(),0);
            //获得版本号
            return info.versionCode;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}

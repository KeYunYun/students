package com.example.kcy.login;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;




public class LoginHttpUtils {


	/*public static void requestNetForGetLogin(final Handler handler ,final String username, final String password) {


		new Thread(new Runnable() {

			@Override
			public void run() {


				//使用UrlConncetion请求服务器将用户密码发送服务器验证
				try{
					//1.创建一个URL对象
					URL url = new URL("http://10.1.110.12:8080/itheima74/servlet/LoginServlet?username="+URLEncoder.encode(username,"utf-8")+"&pwd="+URLEncoder.encode(password,"utf-8"));

					//2.通过URL对象获取一个HttpUrlConnection对象
					HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
					//3.设置HttpUrlConnection对象的一些参数，请求方式，连接的超时时间
					openConnection.setRequestMethod("GET");
					openConnection.setConnectTimeout(10*1000);
					//4.获取响应码，判断响应码是否是200
					int code = openConnection.getResponseCode();
					if(code == 200){
						InputStream inputStream = openConnection.getInputStream();
						//5.获取网络链接的读取流信息，将流转换成字符串。 ByteArrayOutputStream
						String result = StreamUilts.getInputToStream(inputStream);

						boolean issuccess = false;
						System.out.println(result);
						if(result.contains("success"))
						{
							issuccess = true;
						}

						Message msg = Message.obtain();
						msg.what = 1;//指定msg的code ，在接受的时候可以根据what判断是哪个msg
						msg.obj = result;
						handler.sendMessage(msg);

					}

				}catch (Exception e) {
					e.printStackTrace();
				}


			}
		}).start();




	}

	public static void requestNetForPostLogin(final Handler handler ,final String username, final String password) {



		new Thread(new Runnable() {

			@Override
			public void run() {

				//使用UrlConncetion请求服务器将用户密码发送服务器验证
				try{
					//1.创建一个URL对象
					URL url = new URL("http://10.1.110.12:8080/itheima74/servlet/LoginServlet");

					//2.通过URL对象获取一个HttpUrlConnection对象
					HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
					//3.设置HttpUrlConnection对象的一些参数，请求方式，连接的超时时间
					openConnection.setRequestMethod("POST");
					openConnection.setConnectTimeout(10*1000);
					//设置一些请求头的信息   field:http请求的请求头 newValue:请求头的值
					String body = "username="+URLEncoder.encode(username,"utf-8")+"&pwd="+URLEncoder.encode(password,"utf-8");
					//			openConnection.setRequestProperty("Content-Length", body.length()+"");
					//			openConnection.setRequestProperty("Cache-Control", "max-age=0");
					//			openConnection.setRequestProperty("Origin", "http://192.168.13.83:8080");

					//设置UrlConnection可以写请求的内容
					openConnection.setDoOutput(true);//默认值是false
					//获取一个outputstream，并将内容写入该流
					openConnection.getOutputStream().write(body.getBytes());

					//4.获取响应码，判断响应码是否是200
					int code = openConnection.getResponseCode();
					if(code == 200){
						InputStream inputStream = openConnection.getInputStream();
						//5.获取网络链接的读取流信息，将流转换成字符串。 ByteArrayOutputStream
						String result = StreamUilts.getInputToStream(inputStream);
						boolean issuccess = false;
						System.out.println(result);
						if(result.contains("success"))
						{
							issuccess = true;
						}

						Message msg = Message.obtain();
						msg.what = 2;//指定msg的code ，在接受的时候可以根据what判断是哪个msg
						msg.obj = result;
						handler.sendMessage(msg);
					}

				}catch (Exception e) {
					e.printStackTrace();
				}



			}
		}).start();




	}*/



}

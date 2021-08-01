package com.example.twinkle.hyjj4.java;

import android.os.Environment;
import android.util.Log;

import org.apache.http.util.EncodingUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IOUtils {

    public   String html="";
    //指定编码格式 ，把输入流转化为字符串
    public SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
    public Date curDate =  new Date(System.currentTimeMillis());



    public static String getHtml(InputStream is, String encoding)
            throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        is.close();
        return new String(bos.toByteArray(), encoding);
    }

    //读取文件
    public String readDataFromSD(String string) {
        String res = "";
        try {

           /* 创建File对象，确定需要读取文件的信息 */
            File file = new File(Environment.getExternalStorageDirectory() + "/cdnu", string);

            /* FileInputSteam 输入流的对象， */
            FileInputStream fis = new FileInputStream(file);
            /* 准备一个字节数组用户装即将读取的数据 */
            byte[] buffer = new byte[fis.available()];

          /* 开始进行文件的读取 */
            fis.read(buffer);
            /* 关闭流  */
            fis.close();

           /* 将字节数组转换成字符创， 并转换编码的格式 */
            res = EncodingUtils.getString(buffer, "UTF-8");


        } catch (Exception ex) {

        }
        return res;
    }

    //信息写入本地
    public void writeFileSdcard(String fileName, String message) {
        try {
            File fp = new File(Environment.getExternalStorageDirectory() + "/hyjj4", fileName);
            FileOutputStream fos = new FileOutputStream(fp, true);
            byte[] buffer = message.getBytes();
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //本地信息的删除
    public  void delFile(String fileName) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/cdnu", fileName);
            if (file.isFile()) {
                file.delete();
            }
            file.exists();
        } catch (Exception e) {
        }
    }


    //创建hjyy4文件夹
    public void createSDCardDir() {
        File sdcardDir = Environment.getExternalStorageDirectory();
        //得到一个路径，内容是sdcard的文件夹路径和名字
        String path = sdcardDir.getPath() + "/hyjj4";
        File path1 = new File(path);
        if (!path1.exists()) {
            //若不存在，创建目录，可以在应用启动的时候创建
            path1.mkdirs();
        }
    }

    //判断文件是否存在
    public  boolean checkFile(String fileName) {

        String data = formatter.format(curDate)+".txt";      //2017-08-31.txt
        boolean flag = false;

        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/hyjj4", fileName);     //2017-08-31.txt
            if (file.isFile()) {
                if(fileName.equals(data)||data.equals(fileName)||fileName ==data){
                    flag = true;
                }
            }
            else {
                flag = false;
            }
            file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public String getFile(String article){


        try{
            File in = new File(Environment.getExternalStorageDirectory() + "/hyjj4/"+article);
            Document doc = Jsoup.parse(in, "utf-8", "");
            Elements eleCourse = doc.select("div.article_text");
            html = eleCourse.toString();
            System.out.println(doc.html());

        }catch (Exception e){
              e.printStackTrace();
        }

        return  html;

    }




}

package com.example.twinkle.hyjj4.java;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.cache.SchedulingStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by TWINKLE on 2017/8/29.
 */

public class LoginMain {


    public String mainlogin_url = "http://119.29.98.60:8080/HYJJ4/login.jsp";
    public String mainlogin_url_servlet = "http://119.29.98.60:8080/HYJJ4/LoginServlet";
    public String getMainlogin_url ="http://119.29.98.60:8080/HYJJ4/look.jsp";
    public String  upgrade_url_servlet = "http://119.29.98.60:8080/HYJJ4/UpgradeServlet";
    public String  upgrade_url = "http://119.29.98.60:8080/HYJJ4/upgrade.jsp";
    public String getTree_url = "http://119.29.98.60:8080/HYJJ4/treelook.jsp";
    public String upgradetree_url_servlet = "http://119.29.98.60:8080/HYJJ4/AddtreeServlet";
    public String upgradetree_url = "http://119.29.98.60:8080/HYJJ4/treeadd.jsp";
    public String getarticle_url = "http://w.ihx.cc/home";
    public String info = "";

    public String getallinfo[];
    public  String tree_name[];
    public String tree_info[];
    public String tree_time[];



    public String login_main(String admin ,String password) throws  Exception {
        HttpPost loginPost = new HttpPost(mainlogin_url_servlet);// 创建登录的Post请求
        HttpClient httpclient = new DefaultHttpClient();
        loginPost.setHeader("Referer", mainlogin_url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("admin",admin ));
        params.add(new BasicNameValuePair("password",password ));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                params, "utf-8");
        loginPost.setEntity(entity);
        httpclient.execute(loginPost);

        HttpResponse responseMain = httpclient.execute(loginPost);
        InputStream is = responseMain.getEntity().getContent();
        String html = "";
        html = IOUtils.getHtml(is, "utf-8");
        info = Jsoup.parse(html).getElementsByAttribute("name").text();
        System.out.println(info);
        return info;

    }

    public String[] get_admininfo(String admin) throws  Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet mainGet = new HttpGet(getMainlogin_url);
        mainGet.setHeader("Referer", mainlogin_url);
        HttpResponse responseMain = client.execute(mainGet);
        InputStream is = responseMain.getEntity().getContent();
        String   html = "";
        try {

            html = IOUtils.getHtml(is, "utf-8");
            Document doc = Jsoup.parse(html);
            Elements eleCourse = doc.select("td");
            getallinfo = new String[7];
            for(int x=0;x<eleCourse.size()/7-1;x++){
                if(eleCourse.get(x*7).text().toString().equals(admin)){
                    for(int y = 0;y<7;y++)
                    getallinfo[y] = eleCourse.get(7 * x+y).text().toString();
    }

}
        } catch (Exception e) {
            Log.i("query", "query解析失败");
            e.printStackTrace();
        }
        Log.i("query", "query解析成功");
        System.out.println("test"+ Arrays.toString(getallinfo));
       return  getallinfo;

    }

public  boolean upgrade_info(String admin,String password,String name,String say,String level,String xuehao,String xuehaopassword )
        throws Exception{

    HttpPost loginPost = new HttpPost(upgrade_url_servlet);// 创建登录的Post请求
    HttpClient httpclient = new DefaultHttpClient();
    loginPost.setHeader("Referer", upgrade_url);
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("admin",admin ));
    params.add(new BasicNameValuePair("password",password ));
    params.add(new BasicNameValuePair("name",name ));
    params.add(new BasicNameValuePair("say",say ));
    params.add(new BasicNameValuePair("level",level ));
    params.add(new BasicNameValuePair("xuehao",xuehao ));
    params.add(new BasicNameValuePair("xuehaopassword",xuehaopassword ));
    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
            params, "utf-8");
    loginPost.setEntity(entity);
    httpclient.execute(loginPost);

    return true;
}


    public String[] query_treename() throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpGet mainGet = new HttpGet(getTree_url);
        mainGet.setHeader("Referer", getTree_url);
        HttpResponse responseMain = client.execute(mainGet);
        InputStream is = responseMain.getEntity().getContent();
        String   html = "";
        try {
            html = IOUtils.getHtml(is, "utf-8");
            Document doc = Jsoup.parse(html);
            Elements eleCourse = doc.select("td");
            tree_name = new String[eleCourse.size()/3-1];
            for (int y = 0; y < eleCourse.size()/3-1; y++) {
                tree_name[y] = eleCourse.get(3 * y+3).text().toString();
            }


        } catch (Exception e) {
            Log.i("query", "query解析失败");
            e.printStackTrace();
        }
        System.out.println("tree_name"+Arrays.toString(tree_name));
        Log.i("query", "query解析成功");
        return tree_name;
    }

    public String[] query_treeinfo() throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpGet mainGet = new HttpGet(getTree_url);
        mainGet.setHeader("Referer", getTree_url);
        HttpResponse responseMain = client.execute(mainGet);
        InputStream is = responseMain.getEntity().getContent();
        String   html = "";
        try {
            html = IOUtils.getHtml(is, "utf-8");
            Document doc = Jsoup.parse(html);
            Elements eleCourse = doc.select("td");
            tree_info = new String[eleCourse.size()/3-1];
            for (int y = 0; y < eleCourse.size()/3-1; y++) {
                tree_info[y] = eleCourse.get(3 * y+4).text().toString();
            }


        } catch (Exception e) {
            Log.i("query", "query解析失败");
            e.printStackTrace();
        }
        System.out.println("tree_info"+Arrays.toString(tree_info));
        Log.i("query", "query解析成功");
        return tree_info;
    }


    public String[] query_treetime() throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpGet mainGet = new HttpGet(getTree_url);
        mainGet.setHeader("Referer", getTree_url);
        HttpResponse responseMain = client.execute(mainGet);
        InputStream is = responseMain.getEntity().getContent();
        String   html = "";
        try {
            html = IOUtils.getHtml(is, "utf-8");
            Document doc = Jsoup.parse(html);
            Elements eleCourse = doc.select("td");
            tree_time = new String[eleCourse.size()/3-1];
            for (int y = 0; y < eleCourse.size()/3-1; y++) {
                tree_time[y] = eleCourse.get(3 * y+5).text().toString();
            }


        } catch (Exception e) {
            Log.i("query", "query解析失败");
            e.printStackTrace();
        }
        System.out.println("tree_time"+Arrays.toString(tree_time));
        Log.i("query", "query解析成功");
        return tree_time;
    }

    public  boolean upgrade_tree(String name,String information,String time)
            throws Exception{

        HttpPost loginPost = new HttpPost(upgradetree_url_servlet);// 创建登录的Post请求
        HttpClient httpclient = new DefaultHttpClient();
        loginPost.setHeader("Referer", upgradetree_url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name",name ));
        params.add(new BasicNameValuePair("information",information ));
        params.add(new BasicNameValuePair("time",time ));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                params, "utf-8");
        loginPost.setEntity(entity);
        httpclient.execute(loginPost);
        return true;
    }


    public String get_article() throws Exception{


        HttpClient client = new DefaultHttpClient();
        HttpGet mainGet = new HttpGet(getarticle_url);
        mainGet.setHeader("Referer", "http://w.ihx.cc");
        mainGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0");
        HttpResponse responseMain = client.execute(mainGet);
        InputStream is = responseMain.getEntity().getContent();
        String   html = "";
        try {
            html = IOUtils.getHtml(is, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return html;


    }



}

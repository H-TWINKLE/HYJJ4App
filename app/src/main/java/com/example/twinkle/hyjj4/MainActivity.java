package com.example.twinkle.hyjj4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle.hyjj4.java.IOUtils;
import com.example.twinkle.hyjj4.java.LoginMain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
    public Date curDate =  new Date(System.currentTimeMillis());
    public  String text;
    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //new
        setTitle("主页");
        navigationView.setItemIconTintList(null);

        SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "名字");
        String level = preferences.getString("level","0").replaceAll("\\s*", "");
        String say = preferences.getString("say", "个性签名");


        View headerView = navigationView.getHeaderView(0);
        ImageView imageView_pic = (ImageView)headerView.findViewById(R.id.imageView_pic);
        imageView_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check_login();
            }
        });

       TextView textView_admin = (TextView)headerView.findViewById(R.id.textView_name);
        textView_admin.setText(name);
        textView_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check_login();
            }
        });
        TextView textView_info = (TextView)headerView.findViewById(R.id.textView_info);
        textView_info.setText(say);
        textView_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               check_login();
            }
        });


        ImageView imageView_level = (ImageView)headerView.findViewById(R.id.imageView_level);

         if(level.equals("0")||level.equals("null")){
            imageView_level.setImageResource(R.drawable.level0);
          }

        else if(level.equals("1")){
            imageView_level.setImageResource(R.drawable.level1);
        }

       else if(level.equals("2")){
            imageView_level.setImageResource(R.drawable.level2);
        }

       else if(level.equals("3")){
            imageView_level.setImageResource(R.drawable.level3);
        }

       else if(level.equals("4")){
            imageView_level.setImageResource(R.drawable.level4);
        }

       else if(level.equals("5")){
            imageView_level.setImageResource(R.drawable.level5);
        }

        else {
            imageView_level.setImageResource(R.drawable.level6);
        }

         setWebView();




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          //  super.onBackPressed();


            Toast.makeText(getApplicationContext(), "正在退出程序", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //如果是服务里调用，必须加入new task标识
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);


        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.index) {
            setWebView();
            // Handle the camera action
        } else if (id == R.id.jwgl) {
            Intent intent = new Intent(MainActivity.this,JwglActivity.class);
            startActivity(intent);

        } else if (id == R.id.exam) {
            Toast.makeText(getApplicationContext(), "敬请期待", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.tree) {
            Intent intent = new Intent(MainActivity.this,TreeActivity.class);
            startActivity(intent);

        }else if (id == R.id.tool) {
            Intent intent = new Intent(MainActivity.this,ToolActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void check_login(){

        SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
        String  check_login = preferences.getString("admin","admin");
        Log.i("admin",check_login);
        if(check_login.equals("admin")){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);

        }else{
            Intent intent = new Intent(MainActivity.this,InfoActivity.class);
            startActivity(intent);

        }
    }


    public String  set_article(){

        new Thread() {
            public void run() {
                try {

                    String article = formatter.format(curDate)+".txt";  //2017-08-31.txt
                    IOUtils ioUtils = new IOUtils();
                    ioUtils.createSDCardDir();    //hyjj4
                    if(ioUtils.checkFile(article))            //2017-08-31.txt
                    {
                        System.out.println("本地时间相同的文件");
                      text =   ioUtils.getFile(article);


                    }else {
                        System.out.println("本地不存在或者时间不同");
                        //   ioUtils.delFile(article);
                        LoginMain loginMain = new LoginMain();
                        ioUtils.writeFileSdcard(article,loginMain.get_article());
                      text =  ioUtils.getFile(article);
                    }
                } catch (Exception e) {

                    e.printStackTrace();

                }

            }
        }.start();
       return text;

    }


    public boolean  setWebView(){


        setTitle("每日一文");
        webView = (WebView) findViewById(R.id.WebView_main);
        webView.loadUrl("https://meiriyiwen.com/");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
            public void onReceivedError(WebView view, int errorCode,String description, String failingUrl){
                super.onReceivedError(view, errorCode, description, failingUrl);
               webView.loadUrl("file:///android_asset/err.html");
                setTitle("主页");
            }
        });

        return true;
    }


}



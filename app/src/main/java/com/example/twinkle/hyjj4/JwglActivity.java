package com.example.twinkle.hyjj4;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.text.SimpleDateFormat;
import java.util.Date;

public class JwglActivity extends AppCompatActivity {

    WebView webView;
    Dialog dialog;
    public SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
    public Date curDate =  new Date(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jwgl);
        ImageView imageView = (ImageView)findViewById(R.id.imageView_jwgl_finish);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imageView_examinfo = (ImageView)findViewById(R.id.imageView_examinfo);
        imageView_examinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(JwglActivity.this,ExaminfoActivity.class);
                startActivity(intent);*/

              webview();




            }
        });



    }


    public  void webview(){



        dialog = new Dialog(JwglActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_examinfo_web);
        dialog.setCancelable(true);
        dialog.show();

        FloatingActionButton floatingActionButton_jwglinfo_web = (FloatingActionButton)dialog.findViewById(R.id.fab_jwgl_finsh);
        floatingActionButton_jwglinfo_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        webView = (WebView) dialog.findViewById(R.id.Webview);
        webView.loadUrl("http://jwc.cdnu.edu.cn/item/list.asp?id=1652");
        webView.getSettings().setJavaScriptEnabled(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
            public void onReceivedError(WebView view, int errorCode,String description, String failingUrl){
                super.onReceivedError(view, errorCode, description, failingUrl);
                webView.loadUrl("file:///android_asset/err.html");
            }
        });


    }



}

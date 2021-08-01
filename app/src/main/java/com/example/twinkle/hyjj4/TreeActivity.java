package com.example.twinkle.hyjj4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle.hyjj4.java.LoginMain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TreeActivity extends AppCompatActivity {




    public  String  tree_name[],tree_info[],tree_time[];
    public   int tree_flag;
    public SimpleAdapter adapter;
    public EditText editText_send;
    public SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
    public Date curDate =  new Date(System.currentTimeMillis());
    public  String tree_name_value,tree_info_valve,tree_time_value;
    public  boolean flag;
    public TextView textView_warm,textView_say,textView_time,textView_information;
    public  ImageView imageView_pic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        imageView_pic = (ImageView)findViewById(R.id.imageView_mainpic);
        textView_say = (TextView)findViewById(R.id.textView_ls_sayy);
        textView_time = (TextView)findViewById(R.id.textView_ls_timee);
        textView_information=(TextView)findViewById(R.id.textView_ls_information);
        textView_warm=(TextView)findViewById(R.id.textView_warm);

        editText_send = (EditText)findViewById(R.id.editText_send);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_tree_send);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                tree_name_value = preferences.getString("name", "名字");
                tree_info_valve = editText_send.getText().toString();
                tree_time_value = formatter.format(curDate);


               if(tree_info_valve.length()==0){
                   Snackbar.make(view, "请填写此字段", Snackbar.LENGTH_SHORT)
                           .setAction("Action", null).show();
               }else {
                   if(tree_name_value.equals("名字")){
                       tree_name_value = android.os.Build.MODEL;
                   }
                   final Handler handler = new Handler();
                       new Thread(){
                           public  void run() {

                               try{
                                   LoginMain loginMain = new LoginMain();
                                    flag = loginMain.upgrade_tree(tree_name_value,tree_info_valve,tree_time_value);

                               }catch (Exception e){
                                   e.printStackTrace();
                               }
                               handler.post(new Runnable() {
                                   @Override
                                   public void run() {
                                       if(flag){
                                           setlistview();
                                           Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
                                           editText_send.getText().clear();
                                       }

                                   }
                               });

                                }
                       }.start();
                   }


            }
        });



        setlistview();

    }


    public void setlistview_tree_value(){
                LoginMain loginMain = new LoginMain();
                try{

                     tree_name =loginMain.query_treename();
                     tree_flag = tree_name.length;
                     tree_info = loginMain.query_treeinfo();
                     tree_time = loginMain.query_treetime();
                }catch (Exception e){
                    e.printStackTrace();
                }
    }

    public void setlistview_view(){
        ListView listView = (ListView) findViewById(R.id.listview_tree);


        int tree_pic[] = new int[]{
                R.drawable.p0,
                R.drawable.p1,
                R.drawable.p2,
                R.drawable.p3,
                R.drawable.p4,
                R.drawable.p5,
                R.drawable.p6,
                R.drawable.p7,
                R.drawable.p8,
                R.drawable.p9,
        };
        Log.i("tree_name", Arrays.toString(tree_name));

        List<HashMap<String,Object>> mylists = new ArrayList<HashMap<String,Object>>();
        for(int x=0;x<tree_flag;x++){
            HashMap<String,Object> map = new HashMap<String,Object>();
            Random random = new Random();
            int randomint = random.nextInt(10);
            map.put("img",tree_pic[randomint]);
            map.put("itemsay", tree_name[x]);
            map.put("iteminformation",tree_info[x]);
            map.put("itemtime",tree_time[x]);
            mylists.add(map);
        }
        //配置适配器
        adapter = new SimpleAdapter(this,
                mylists,//数据源
                R.layout.listview_tree,//显示布局
                new String[]{"itemsay","itemtime","img","iteminformation"}, //数据源的属性字段
                new int[]{R.id.textView_ls_sayy,R.id.textView_ls_timee,R.id.imageView_mainpic,R.id.textView_ls_information}); //布局里的控件id
        //添加并且显示
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(tree_flag==0){
            textView_warm.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    public  void setlistview(){
        final Handler handler = new Handler();
        new Thread() {
            public void run() {
                try {
                    setlistview_tree_value();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        setlistview_view();
                        adapter.notifyDataSetChanged();


                    }
                });
            }
        }.start();
    }

}

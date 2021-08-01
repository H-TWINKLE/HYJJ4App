package com.example.twinkle.hyjj4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle.hyjj4.java.LoginMain;

public class InfoActivity extends AppCompatActivity {


    public  String admininfo[] = new String[7];
    public  EditText editText_info_admin ,editText_info_name,editText_info_say,editText_info_level,editText_info_xuehao,editText_info_xuehaomima;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_info);

        editText_info_admin = (EditText)findViewById(R.id.editText_info_admin);
        editText_info_name = (EditText)findViewById(R.id.editText_info_name);
        editText_info_say = (EditText)findViewById(R.id.editText_info_say);
        editText_info_level = (EditText)findViewById(R.id.editText_info_level);
        editText_info_xuehao = (EditText)findViewById(R.id.editText_info_xuehao);
        editText_info_xuehaomima = (EditText)findViewById(R.id.editText_info_xuehaopassword);

        writeinfo();
        setimageButton_finish();
        setbutton_quit_admin();
        setbutton_upgrade();




    }



    public void writeinfo() {

        SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
        String admin = preferences.getString("admin", "admin");
        String name = preferences.getString("name", "名字");
        String say = preferences.getString("say", "个性签名");
        String level = preferences.getString("level", "defaultname");
        String xuehao = preferences.getString("xuehao", "defaultname");
        String xuehaopassword = preferences.getString("xuehaopassword", "defaultname");

        editText_info_admin.setText(admin);
        editText_info_name.setText(name);
        editText_info_say.setText(say);
        editText_info_level.setText(level);
        editText_info_xuehao.setText(xuehao);
        editText_info_xuehaomima.setText(xuehaopassword);

    }

    public  void  setimageButton_finish(){
        ImageView imageView = (ImageView)findViewById(R.id.imageView_info_finish);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}});
    }

    public void setbutton_quit_admin(){
        Button button_info_quitadmin = (Button)findViewById(R.id.button_info_quitadmin);
        button_info_quitadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("admin");
                editor.remove("name");
                editor.remove("say");
                editor.remove("level");
                editor.commit();
                finish();
                Intent intent = new Intent(InfoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public  void  setbutton_upgrade(){

        SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);

        final String password = preferences.getString("password", "defaultname");

        Button button_info_upgrade = (Button)findViewById(R.id.button_info_upgrade);
        button_info_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    public  void  run(){
                        LoginMain loginMain  = new LoginMain();
                        try{

                            String admin = editText_info_admin.getText().toString();
                            String name = editText_info_name.getText().toString();
                            String say = editText_info_say.getText().toString();
                            String level = editText_info_level.getText().toString();
                            String xuehao = editText_info_xuehao.getText().toString();
                            String xuehaopassword = editText_info_xuehaomima.getText().toString();


                            boolean flag =  loginMain.upgrade_info(admin,password,name,say,level,xuehao,xuehaopassword);
                            if(flag){
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("admin", admin);
                                editor.putString("password",password);
                                editor.putString("name", name);
                                editor.putString("say", say);
                                editor.putString("level",level);
                                editor.putString("xuehao", xuehao);
                                editor.putString("xuehaopassword", xuehaopassword);
                                editor.commit();
                                finish();
                                Intent intent = new Intent(InfoActivity.this,MainActivity.class);
                                startActivity(intent);
                                Looper.loop();
                            }

                        }catch (Exception e){

                        }

                    }
                }.start();
            }
        });




    }

}

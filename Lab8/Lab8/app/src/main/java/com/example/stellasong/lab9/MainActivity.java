package com.example.stellasong.lab9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by StellaSong on 2017/11/28.
 */

public class MainActivity extends AppCompatActivity {
    public Button ok;
    public Button clear;
    private EditText et_password;
    private EditText et_password2;
    private boolean tag = true; // 真表示创建密码，假表示输入密码
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        setContentView(R.layout.activity_main);

        ok = (Button)findViewById(R.id.BtnOK);
        clear = (Button)findViewById(R.id.BtnClear1);
        et_password = (EditText)findViewById(R.id.editText1);
        et_password2 = (EditText)findViewById(R.id.editText2);

        //读取保存在本地的用户名和密码
        readAccount();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得用户输入的用户名和密码
                String password1 = et_password.getText().toString();
                String password2 = et_password2.getText().toString();

                //创建sharedPreference对象，info表示文件名，MODE_PRIVATE表示访问权限为私有的
                SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);

                //获得sp的编辑器
                SharedPreferences.Editor ed = sp.edit();

                //以键值对的显示将用户名和密码保存到sp中
                ed.putString("password1", password1);
                ed.putString("password2", password2);

                //提交用户名和密码
                ed.commit();

                // 提交以及失败判定逻辑
                if ("".equals(password1) || "".equals(password2)) {
                    Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_LONG).show();
                } else if (!password1.equals(password2)) {
                    Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_LONG).show();
                } else if(password1.equals(password2)) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, FileEdit.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == true) {
                    et_password.setText("");
                    et_password2.setText("");
                } else {
                    et_password.setText("");
                }
            }
        });
    }

    //读取保存在本地密码
    public void readAccount() {
        //创建SharedPreferences对象
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);

        //获得保存在SharedPredPreferences中的密码和确认密码
        String password1 = sp.getString("password1", "");
        String password2 = sp.getString("password2", "");

        //在输入框中显示密码和确认密码
        et_password.setText(password1);
        et_password2.setText(password2);

        if(!"".equals(password2)) {
            et_password2.setVisibility(View.INVISIBLE);
            et_password.setHint("Password");
            et_password.setText("");
            tag = false;
        }
    }
}

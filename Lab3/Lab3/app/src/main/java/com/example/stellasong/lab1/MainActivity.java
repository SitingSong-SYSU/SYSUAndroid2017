package com.example.stellasong.lab1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.support.design.R.styleable.CoordinatorLayout;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radio = null;
    private RadioButton radioStu = null;
    private RadioButton radioTeacher = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonLogin = (Button) findViewById(R.id.login);
        Button buttonRegister = (Button) findViewById(R.id.register);
        ImageView mImage = (ImageView)findViewById(R.id.imageView);
        final EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        radio = (RadioGroup) findViewById(R.id.radioGroup);
        radioStu = (RadioButton) findViewById(R.id.radioStu);
        radioTeacher = (RadioButton) findViewById(R.id.radioTeacher);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] arrayOfString = {"拍摄", "从相册选择"};
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("上传头像")
                        .setItems(arrayOfString, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                                Toast.makeText(MainActivity.this, "您选择了[" + arrayOfString[paramAnonymousInt] + "]", Toast.LENGTH_SHORT).show();
                                paramAnonymousDialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "您选择了[取消]", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }
        });


        buttonLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextUsername.getText().toString())) {
                    Toast.makeText(MainActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (editTextUsername.getText().toString().equals("123456") && editTextPassword.getText().toString().equals("6666")) {
                    Snackbar.make(getWindow().getDecorView(),"登录成功", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,"“Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                        }
                    }).show();

                } else {
                    Snackbar.make(getWindow().getDecorView(),"学号或密码错误", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,"“Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            }

        });

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radioStu.getId()) {
                    Snackbar.make(getWindow().getDecorView(),"您选择了学生", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,"“Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
                if (checkedId == radioTeacher.getId()) {
                    Snackbar.make(getWindow().getDecorView(),"您选择了教职工", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,"“Snackbar 的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int checkedId = radioGroup.getCheckedRadioButtonId();
                if (checkedId == R.id.radioStu) {
                    Toast.makeText(MainActivity.this, "学生身份注册功能尚未开启", Toast.LENGTH_SHORT).show();
                } else if (checkedId == radioTeacher.getId()) {
                    Toast.makeText(MainActivity.this, "教职工身份注册功能尚未开启", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

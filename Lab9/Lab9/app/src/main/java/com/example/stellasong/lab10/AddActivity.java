package com.example.stellasong.lab10;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by StellaSong on 2017/12/4.
 */

public class AddActivity extends AppCompatActivity {
    // 初始化控件
    public Button addBtn;
    public EditText addET;
    public EditText birthET;
    public EditText giftET;

    private void findViewById() {
        addBtn = (Button) findViewById(R.id.BtnAdd);
        addET = (EditText) findViewById(R.id.nameET);
        birthET = (EditText) findViewById(R.id.birthET);
        giftET = (EditText) findViewById(R.id.giftET);
    }

    // 创建类
    private static final String TABLE_NAME = "Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        findViewById();

        // 增加条目的编辑界面增加按钮事件
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById();
                if (addET.length() == 0) {
                    Toast.makeText(AddActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    findViewById();

                    String namet = addET.getText().toString();
                    String birtht = birthET.getText().toString();
                    String giftt = giftET.getText().toString();

                    MyDataBase db = new MyDataBase(getBaseContext());
                    SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("select * from " +
                            TABLE_NAME + " where name like ?", new String[]{namet});

                    if (cursor.moveToFirst() == true) {
                        Toast.makeText(AddActivity.this, "姓名不能重复", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("name", namet);
                        contentValues.put("birth", birtht);
                        contentValues.put("gift", giftt);
                        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
                        sqLiteDatabase.close();
                        setResult(99, new Intent());
                        finish();
                    }
                }

            }
        });
    }
}
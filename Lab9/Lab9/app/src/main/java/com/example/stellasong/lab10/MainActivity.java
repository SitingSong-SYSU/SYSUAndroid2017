package com.example.stellasong.lab10;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by StellaSong on 2017/12/4.
 */

public class MainActivity extends AppCompatActivity {
    // 初始化控件
    public Button add;

    public ListView LV;
    public SimpleAdapter adapter;

    public TextView nameTV;
    public EditText birthET;
    public EditText giftET;
    public TextView phone;

    private static final String TABLE_NAME = "Info";
    public List<Map<String, String>> datas = new ArrayList<Map<String, String>>();

    // 将数据库中的数据更新到UI中
    private void dataUpdate() {
        try {
            MyDataBase db = new MyDataBase(getBaseContext());
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
            datas  = new ArrayList<Map<String, String>>();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name1 = cursor.getString(0);
                    String birth1 = cursor.getString(1);
                    String gift1 = cursor.getString(2);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", name1);
                    map.put("birth", birth1);
                    map.put("gift", gift1);
                    datas .add(map);
                }
                adapter = new SimpleAdapter(MainActivity.this, datas, R.layout.item,
                        new String[]{"name", "birth", "gift"}, new int[]{R.id.nameLV, R.id.birthLV, R.id.giftLV});
                LV.setAdapter(adapter);
            }
        } catch (SQLException e) {
            Log.i("DataBaseLog", e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LV = (ListView)findViewById(R.id.Start);
        add = (Button)findViewById(R.id.addList);

        // 打开应用时初始化列表为数据库数据
        dataUpdate();

        // 单击列表进入增加条目界面
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 使用LayoutInflater实现自定义对话框
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                View newView = factory.inflate(R.layout.dialoglayout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                nameTV = (TextView)newView.findViewById(R.id.nameDL);
                birthET = (EditText)newView.findViewById(R.id.birthDL);
                giftET = (EditText)newView.findViewById(R.id.giftDL);
                phone = (TextView)newView.findViewById(R.id.phoneDL);

                nameTV.setText(datas.get(position).get("name"));
                birthET.setText(datas.get(position).get("birth"));
                giftET.setText(datas.get(position).get("gift"));

                // 动态权限申请
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},0);
                }

                // 读取联系人列表
                String str1 = "";
                Cursor cursor1 = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                while (cursor1.moveToNext()) {
                    String str2 = cursor1.getString(cursor1.getColumnIndex("_id"));
                    if (cursor1.getString(cursor1.getColumnIndex("display_name"))
                            .equals(nameTV.getText().toString())) {
                        // 判断某条联系人的信息中，是否有电话号码
                        if (Integer.parseInt(cursor1.getString(cursor1
                                .getColumnIndex("has_phone_number"))) > 0) {
                            // 取出该条联系人信息中的电话号码
                            Cursor cursor2 = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    "contact_id = " + str2,
                                    null,
                                    null
                            );
                            while (cursor2.moveToNext()) {
                                str1 = str1 + cursor2.getString(cursor2.getColumnIndex("data1")) + "\n";
                            }
                            cursor2.close();
                        }
                    }
                }
                cursor1.close();
                // 如果手机通讯录中没有对应的联系人则将手机设为无
                if (str1.equals("")) str1 = "无";
                phone.setText(str1);

                // 自定义对话框
                builder.setView(newView);
                builder.setTitle(" ( ゜- ゜)つロ 乾杯~");
                builder.setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (birthET.length() != 0) {
                            MyDataBase db = new MyDataBase(getBaseContext());
                            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                            sqLiteDatabase.execSQL("update " + TABLE_NAME + " set birth = ? where name = ?",
                                    new Object[]{birthET.getText().toString(), nameTV.getText().toString()});
                            sqLiteDatabase.close();
                        }
                        if (giftET.length() != 0) {
                            MyDataBase db = new MyDataBase(getBaseContext());
                            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                            sqLiteDatabase.execSQL("update " + TABLE_NAME + " set gift = ? where name = ?",
                                    new Object[]{giftET.getText().toString(), nameTV.getText().toString()});
                            sqLiteDatabase.close();
                        }
                        dataUpdate();
                    }
                });
                builder.setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //To do
                    }
                });
                builder.show();
            }
        });

        // 点击增加条目的按钮跳转到编辑界面
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 9);
            }
        });

        // 长按listview弹出询问删除对话框
        LV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                message.setTitle("是否删除？");
                message.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 删除数据库数据
                        MyDataBase db = new MyDataBase(getBaseContext());
                        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                        sqLiteDatabase.execSQL("delete from " + TABLE_NAME  + " where name = ?",
                                new String[]{datas.get(position).get("name")});
                        sqLiteDatabase.close();
                        // 删除列表中的数据
                        datas.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                message.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 不执行操作
                    }
                });
                message.create().show();
                return true;
            }
        });
    }

    // 从其它页面返回时更新数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9 && resultCode == 99) {
            dataUpdate();
        }
    }
}

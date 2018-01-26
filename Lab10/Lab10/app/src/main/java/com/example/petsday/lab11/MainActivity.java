package com.example.petsday.lab11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.petsday.lab11.adapter.CardAdapter;
import com.example.petsday.lab11.factory.ServiceFactory;
import com.example.petsday.lab11.model.User;
import com.example.petsday.lab11.service.GithubService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by StellaSong on 2017/12/12.
 */

public class MainActivity extends AppCompatActivity {
    private EditText searchET;
    private Button clearBT;
    private Button fetchBT;
    private RecyclerView userRV;
    private ProgressBar waitPB;

    // 设置时间对象，用于判断二次查询间隔
    private long Time1 = -1000;
    private long Time2 = -1000;

    private List<User> users;
    private CardAdapter myAdapter;
    private ServiceFactory mServiceFactory;
    private GithubService mGithubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化控件
        searchET = (EditText)findViewById(R.id.searchET);
        clearBT = (Button)findViewById(R.id.clearBT);
        fetchBT = (Button)findViewById(R.id.fetchBT);
        userRV = (RecyclerView)findViewById(R.id.userRV);
        waitPB = (ProgressBar)findViewById(R.id.waitPB);

        // 初始化列表，设置RecyclerView
        users = new ArrayList<User>();
        userRV.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new CardAdapter(users, MainActivity.this);
        userRV.setAdapter(myAdapter);

        // 初始化网络请求服务包
        mServiceFactory = new ServiceFactory();
        mGithubService = mServiceFactory.createGithubService();

        // 设置clear按钮点击监听，清空EditTextview和列表中所有数据
        clearBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchET.setText("");
                users.clear();
                myAdapter.notifyDataSetChanged();
            }
        });

        // 设置fetch按钮点击监听
        fetchBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取搜索框字符串，检查不为空
                String searchS = searchET.getText().toString();
                if (searchS.equals("")) {
                    Toast.makeText(MainActivity.this, "查询栏为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 两次请求时间间隔不小于1s
                Time2 = Time1;
                Time1 = (new Date()).getTime();
                if (Time1 - Time2 < 1000) {
                    Toast.makeText(MainActivity.this, "点击速度过快",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 请求前打开progressBar
                waitPB.setVisibility(View.VISIBLE);
                // 异步处理网络请求
                mGithubService.getUser(searchS)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<User>() {
                            // 请求完成后关闭progressBar
                            @Override
                            public void onCompleted() {
                                waitPB.setVisibility(View.INVISIBLE);
                            }

                            // 请求失败弹出提示
                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, "数据请求失败: "
                                        + e.getMessage(), Toast.LENGTH_LONG).show();
                                waitPB.setVisibility(View.INVISIBLE);
                            }

                            // 请求得到数据添加到列表中
                            @Override
                            public void onNext(User user) {
                                users.add(user);
                                myAdapter.notifyDataSetChanged();
                            }
                        });
            }
        });

        // 设置列表监听事件
        myAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            // 点击使用intent将login内容发送到ReposActivity
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, ReposActivity.class);
                intent.putExtra("login", users.get(position).getLogin());
                startActivityForResult(intent,0);
            }

            // 长按从列表删除选择的项
            @Override
            public void onLongClick(int position) {
                Toast.makeText(getApplicationContext(), "从列表删除" +
                        users.get(position).getLogin(), Toast.LENGTH_SHORT).show();
                users.remove(position);
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}

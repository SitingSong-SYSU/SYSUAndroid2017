package com.example.petsday.lab11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.petsday.lab11.adapter.ListAdapter;
import com.example.petsday.lab11.factory.ServiceFactory;
import com.example.petsday.lab11.model.Repos;
import com.example.petsday.lab11.service.ReposService;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by StellaSong on 2017/12/12.
 */

public class ReposActivity extends Activity {
    private ListView reposLV;
    private ProgressBar waitPB;

    private ListAdapter mListAdapter;
    private ServiceFactory mServiceFactory;
    private ReposService mReposService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);

        // 初始化控件
        reposLV = (ListView)findViewById(R.id.reposLV);
        waitPB = (ProgressBar)findViewById(R.id.waitPB);

        // 初始化网络请求服务包
        mServiceFactory = new ServiceFactory();
        mReposService = mServiceFactory.createReposService();

        // 获取MainActivity发送的login数据
        Intent intent = getIntent();
        final String login = intent.getStringExtra("login");
        // 异步处理网络请求
        mReposService.getRepos(login)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repos>>(){
                    // 请求完成后关闭progressBar，显示列表
                    @Override
                    public void onCompleted() {
                        waitPB.setVisibility(View.INVISIBLE);
                        reposLV.setVisibility(View.VISIBLE);
                    }

                    // 请求失败弹出提示
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ReposActivity.this,"数据请求失败: "
                                + e.getMessage(), Toast.LENGTH_LONG).show();
                        waitPB.setVisibility(View.INVISIBLE);
                    }

                    // 初始化列表，设置ListView，将请求得到数据添加到列表中
                    @Override
                    public void onNext(List<Repos> repos) {
                        mListAdapter = new ListAdapter(repos);
                        reposLV.setAdapter(mListAdapter);
                    }
                });
    }
}

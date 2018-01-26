package com.example.stellasong.lab5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by StellaSong on 2017/10/21.
 */

public class ShowDetails extends Activity {
    private RecyclerView mRecyclerView;
    private int requestCode = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout sec = (LinearLayout)findViewById(R.id.secondview);
        sec.setVisibility(View.INVISIBLE);
        FloatingActionButton flt = (FloatingActionButton)findViewById(R.id.car);
        flt.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        final String ShowName = intent.getStringExtra("Name");
        final String Price = intent.getStringExtra("Price");
        final String info = intent.getStringExtra("Info");
        final Object tag = intent.getStringExtra("Tag");

        mRecyclerView = (RecyclerView)findViewById(R.id.shop);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new DetailAdapter(ShowName, Price, info, ShowDetails.this, ShowDetails.this, tag));
    }
}

package com.example.stellasong.lab5;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by StellaSong on 2017/10/21.
 */

class DetailViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView price;
    TextView cate;
    ImageView picture;
    ImageButton backIcon;
    ImageButton star;
    ImageButton addshop;
    ListView listView;

    public DetailViewHolder(View view){
        super(view);
        name = (TextView)view.findViewById(R.id.nam);
        price = (TextView)view.findViewById(R.id.price);
        picture = (ImageView)view.findViewById(R.id.pic);
        cate = (TextView)view.findViewById(R.id.cate);
        backIcon = (ImageButton)view.findViewById(R.id.back);
        star = (ImageButton)view.findViewById(R.id.star);
        addshop = (ImageButton)view.findViewById(R.id.addshopcar);
        listView = (ListView)view.findViewById(R.id.function);
        star.setTag("0");
    }
}
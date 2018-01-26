package com.example.stellasong.lab5;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by StellaSong on 2017/10/21.
 */

class ShopViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView circle;

    public ShopViewHolder(View view) {
        super(view);
        name = (TextView)view.findViewById(R.id.id_num);
        circle = (TextView)view.findViewById(R.id.cir);
    }
}
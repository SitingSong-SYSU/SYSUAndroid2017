package com.example.stellasong.lab5;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by StellaSong on 2017/10/22.
 */

public class ShopcarViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView circle;
    TextView price;

    public ShopcarViewHolder(View view) {
        super(view);
        name = (TextView)view.findViewById(R.id.iname);
        circle = (TextView)view.findViewById(R.id.icon);
        price = (TextView)view.findViewById(R.id.dollor);
    }
}

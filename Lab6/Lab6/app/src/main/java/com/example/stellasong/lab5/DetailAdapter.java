package com.example.stellasong.lab5;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StellaSong on 2017/10/21.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {
    private String name;
    Activity act;
    private Context context;
    private String price;
    private String info;
    private List<String> stringList;

    public DetailAdapter(String name, String Price, String Info, Context context, Activity act) {
        this.name = name;
        this.context = context;
        this.price = Price;
        this.info = Info;
        this.act = act;
    }

    public com.example.stellasong.lab5.DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        com.example.stellasong.lab5.DetailViewHolder holder
                = new com.example.stellasong.lab5.DetailViewHolder(LayoutInflater.from(context).inflate(R.layout.detail,parent,false));
        return holder;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onBindViewHolder(final DetailViewHolder holder, final int position) {
        holder.name.setText(name);
        holder.price.setText(price);
        holder.cate.setText(info);
        // 设置图片
        switch (name) {
            case "Enchated Forest":
                holder.picture.setImageResource(R.mipmap.enchatedforest);
                break;
            case "Arla Milk":
                holder.picture.setImageResource(R.mipmap.arla);
                break;
            case "Devondale Milk":
                holder.picture.setImageResource(R.mipmap.devondale);
                break;
            case "Kindle Oasis":
                holder.picture.setImageResource(R.mipmap.kindle);
                break;
            case "waitrose 早餐麦片":
                holder.picture.setImageResource(R.mipmap.waitrose);
                break;
            case "Mcvitie's 饼干":
                holder.picture.setImageResource(R.mipmap.mcvitie);
                break;
            case "Ferrero Rocher":
                holder.picture.setImageResource(R.mipmap.ferrero);
                break;
            case "Maltesers":
                holder.picture.setImageResource(R.mipmap.maltesers);
                break;
            case "Lindt":
                holder.picture.setImageResource(R.mipmap.lindt);
                break;
            case "Borggreve":
                holder.picture.setImageResource(R.mipmap.borggreve);
                break;
        }

        // 添加商品详情页面底部列表
        String[] s = new String[]{"一键下单", "分享商品", "不感兴趣", "查看更多促销信息"};
        stringList = new ArrayList<String>();
        for(int i = 0; i < s.length; i++) {
            stringList.add(s[i]);
        }
        FunctionsDescrib fun = new FunctionsDescrib(context, stringList);
        holder.listView.setAdapter(fun);

        final Intent intent = act.getIntent();

        // 设置返回事件
        holder.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act.finish();
            }
        });

        // 设置星星点击事件
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object tag = holder.star.getTag();
                if(tag == "0") {
                    holder.star.setTag("1");
                    holder.star.setImageResource(R.mipmap.full_star);
                } else {
                    holder.star.setTag("0");
                    holder.star.setImageResource(R.mipmap.empty_star);
                }
            }
        });

        // 设置添加购物车事件
        holder.addshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "商品已添加到购物车", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new Goods(name, info, price));
            }
        });
    }
}

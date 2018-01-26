package com.example.stellasong.lab5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by StellaSong on 2017/10/21.
 */

public class ShopcarAdapter extends BaseAdapter {
    private Context context;
    private List<Goods> cargoods;

    public ShopcarAdapter(List<Goods> cargood, Context context) {
        this.context = context;
        this.cargoods = cargood;
    }

    @Override
    public int getCount() {
        if (cargoods != null) {
            return cargoods.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        if (cargoods != null) {
            return cargoods.get(i);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder holder;
        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shopcar_detail, null);
            holder = new ViewHolder();
            holder.icon = (TextView)convertView.findViewById(R.id.icon);
            holder.name = (TextView)convertView.findViewById(R.id.iname);
            holder.price = (TextView)convertView.findViewById(R.id.dollor);
            convertView.setTag(holder);
        } else {
            convertView = view;
            holder = (ViewHolder)convertView.getTag();
        }
        if (cargoods.get(position).getName().substring(0,1).toUpperCase().compareTo("A") > 0
                && cargoods.get(position).getName().substring(0,1).toUpperCase().compareTo("Z") < 0)
                    holder.icon.setText(cargoods.get(position).getName().substring(0,1).toUpperCase());
        else
            holder.icon.setText("*");
        holder.name.setText(cargoods.get(position).getName());
        holder.price.setText(cargoods.get(position).getprice());
        return convertView;
    }

    // 第0行不可以点击
    @Override
    public boolean isEnabled(int position)
    {
        if(position == 0) {
            return false;
        }
        return super.isEnabled(position);
    }

    private class ViewHolder {
        public TextView name;
        public TextView price;
        public TextView icon;
    }
}

package com.example.stellasong.lab5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by StellaSong on 2017/10/21.
 */


public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {
    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    private OnItemClickListener mOnItemClickListener = null;
    private List<Goods> goods;
    private Context context;

    public ShopAdapter(List<Goods> goods, Context context) {
        this.goods = goods;
        this.context = context;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShopViewHolder holder = new ShopViewHolder(LayoutInflater.from(context).inflate(R.layout.item, null, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ShopViewHolder holder, int position){
        holder.name.setText(goods.get(position).getName());
        holder.circle.setText(goods.get(position).getName().substring(0,1).toUpperCase());
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
}

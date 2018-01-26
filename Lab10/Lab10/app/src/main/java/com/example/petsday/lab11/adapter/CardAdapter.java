package com.example.petsday.lab11.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petsday.lab11.R;
import com.example.petsday.lab11.model.User;

import java.util.List;

/**
 * Created by StellaSong on 2017/12/12.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    private OnItemClickListener mOnItemClickListener = null;
    private List<User> users;
    private Context context;

    public CardAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewHolder(LayoutInflater.from(context).inflate(R.layout.list, parent, false));
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        holder.login.setText(users.get(position).getLogin());
        holder.id.setText("" + users.get(position).getId());
        holder.blog.setText(users.get(position).getBlog());
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
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        TextView login;
        TextView id;
        TextView blog;

        public CardViewHolder(View view) {
            super(view);
            login = (TextView)view.findViewById(R.id.headTV);
            id = (TextView)view.findViewById(R.id.desTV1);
            blog = (TextView)view.findViewById(R.id.desTV2);
        }
    }
}

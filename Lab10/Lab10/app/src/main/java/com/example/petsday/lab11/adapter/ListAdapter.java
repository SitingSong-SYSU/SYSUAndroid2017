package com.example.petsday.lab11.adapter;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.petsday.lab11.R;
import com.example.petsday.lab11.model.Repos;

import java.util.List;

/**
 * Created by StellaSong on 2017/12/14.
 */

public class ListAdapter extends BaseAdapter {
    private List<Repos> repos;

    public ListAdapter(List<Repos> repos) {
        this.repos = repos;
    }

    @Override
    public int getCount() {
        return repos != null ? repos.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return repos != null ? repos.get(i) : null;
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
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list, null);
            holder = new ViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.headTV);
            holder.language = (TextView)convertView.findViewById(R.id.desTV1);
            holder.description = (TextView)convertView.findViewById(R.id.desTV2);
            convertView.setTag(holder);
        } else {
            convertView = view;
            holder = (ViewHolder)convertView.getTag();
        }
        holder.name.setText(repos.get(position).getName());
        TextPaint paint = holder.language.getPaint();
        paint.setFakeBoldText(true); // 加粗language
        holder.language.setText(repos.get(position).getLanguage());
        holder.description.setText(repos.get(position).getDescription());
        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView description;
        public TextView language;
    }
}

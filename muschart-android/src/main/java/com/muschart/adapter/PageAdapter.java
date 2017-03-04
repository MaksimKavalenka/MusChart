package com.muschart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.muschart.R;

import java.util.List;

public class PageAdapter extends BaseAdapter {

    private List<Long> pages;
    private LayoutInflater lInflater;

    public PageAdapter(Context context, List<Long> pages) {
        this.pages = pages;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        private Button page;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public Object getItem(int position) {
        return pages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PageAdapter.ViewHolder viewHolder;
        View view = convertView;

        if (convertView == null) {
            view = lInflater.inflate(R.layout.page_list, parent, false);
            viewHolder = new PageAdapter.ViewHolder();
            viewHolder.page = (Button) view.findViewById(R.id.page);
            view.setTag(viewHolder);
        } else
            viewHolder = (PageAdapter.ViewHolder) view.getTag();

        Long page = pages.get(position);
        viewHolder.page.setTag(position);

        viewHolder.page.setText(String.valueOf(page));

        return view;
    }

}
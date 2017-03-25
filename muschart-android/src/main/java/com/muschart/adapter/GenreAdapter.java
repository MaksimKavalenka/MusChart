package com.muschart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muschart.R;
import com.muschart.entity.GenreEntity;
import com.muschart.listener.ContentListener;

import java.util.List;

public class GenreAdapter extends BaseAdapter {

    private List<GenreEntity> genres;
    private ContentListener contentNavigationListener;
    private LayoutInflater lInflater;

    public GenreAdapter(Context context, List<GenreEntity> genres, ContentListener contentNavigationListener) {
        this.genres = genres;
        this.contentNavigationListener = contentNavigationListener;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        private LinearLayout genre;
        private TextView id, name;
    }

    @Override
    public int getCount() {
        return genres.size();
    }

    @Override
    public Object getItem(int position) {
        return genres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GenreAdapter.ViewHolder viewHolder;
        View view = convertView;

        if (convertView == null) {
            view = lInflater.inflate(R.layout.genre_list, parent, false);
            viewHolder = new GenreAdapter.ViewHolder();
            viewHolder.genre = (LinearLayout) view.findViewById(R.id.genre);
            viewHolder.id = (TextView) view.findViewById(R.id.id);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(viewHolder);
        } else
            viewHolder = (GenreAdapter.ViewHolder) view.getTag();

        viewHolder.id.setTag(position);
        viewHolder.name.setTag(position);

        GenreEntity genre = genres.get(position);
        viewHolder.genre.setOnClickListener(v -> contentNavigationListener.navigateToGenreArtists(genre.getId()));
        viewHolder.id.setText(String.valueOf(position + 1));
        viewHolder.name.setText(genre.getName());

        return view;
    }

}
package com.muschart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.muschart.R;
import com.muschart.constants.UrlConstants;
import com.muschart.entity.TrackEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrackAdapter extends BaseAdapter {

    private List<TrackEntity> tracks;
    private Context context;
    private LayoutInflater lInflater;
    private View.OnClickListener click;

    public TrackAdapter(Context context, List<TrackEntity> tracks) {
        this.tracks = tracks;
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        private TextView id, artist, name;
        private ImageView cover;
        private ImageButton play;
    }

    @Override
    public int getCount() {
        return tracks.size();
    }

    @Override
    public Object getItem(int position) {
        return tracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (convertView == null) {
            view = lInflater.inflate(R.layout.track_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.id = (TextView) view.findViewById(R.id.id);
            viewHolder.artist = (TextView) view.findViewById(R.id.artist);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.cover = (ImageView) view.findViewById(R.id.cover);
            viewHolder.play = (ImageButton) view.findViewById(R.id.play);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        TrackEntity track = tracks.get(position);
        viewHolder.id.setTag(position);
        viewHolder.artist.setTag(position);
        viewHolder.name.setTag(position);
        viewHolder.cover.setTag(position);
        viewHolder.play.setTag(position);

        viewHolder.id.setText(String.valueOf(track.getId()));
        viewHolder.name.setText(track.getName());

        StringBuilder artist = new StringBuilder();
        for (int i = 0; i < track.getArtists().size(); i++) {
            if (i > 0) {
                artist.append(track.getUnits().get(i - 1));
            }
            artist.append(track.getArtists().get(i).getName());
        }
        viewHolder.artist.setText(artist);

        Picasso.with(context).load(UrlConstants.MetadataUrlConstants.TRACK_IMAGE_METADATA + "/" + track.getCover()).into(viewHolder.cover);

        createClick();

        viewHolder.play.setOnClickListener(click);
        return view;
    }

    private void createClick() {
        this.click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.play:

                        break;
                    default:
                        break;
                }
            }
        };
    }

}
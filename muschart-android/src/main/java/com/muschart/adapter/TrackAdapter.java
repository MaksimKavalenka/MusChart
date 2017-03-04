package com.muschart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.net.UrlEscapers;
import com.muschart.R;
import com.muschart.activity.MediaActivity;
import com.muschart.constants.UrlConstants;
import com.muschart.entity.TrackEntity;
import com.muschart.utility.Connection;
import com.muschart.utility.Parser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrackAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<TrackEntity> tracks;
    private LayoutInflater lInflater;

    public TrackAdapter(Context context, List<TrackEntity> tracks) {
        this.context = context;
        this.tracks = tracks;
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
        viewHolder.artist.setText(Parser.getFullArtistName(track));
        viewHolder.play.setOnClickListener(this);
        Picasso.with(context).load(UrlEscapers.urlFragmentEscaper().escape(UrlConstants.MetadataUrlConstants.TRACK_IMAGE_METADATA + "/" + track.getCover())).into(viewHolder.cover);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                if (Connection.checkInternetConnection(context)) {
                    Intent intent = new Intent(context, MediaActivity.class);
                    intent.putExtra("artist", Parser.getFullArtistName(tracks.get((Integer) view.getTag())));
                    intent.putExtra("name", tracks.get((Integer) view.getTag()).getName());
                    intent.putExtra("cover", UrlEscapers.urlFragmentEscaper().escape(UrlConstants.MetadataUrlConstants.TRACK_IMAGE_METADATA + "/" + tracks.get((Integer) view.getTag()).getCover()));
                    intent.putExtra("song", UrlEscapers.urlFragmentEscaper().escape(UrlConstants.MetadataUrlConstants.AUDIO_METADATA + "/" + tracks.get((Integer) view.getTag()).getSong()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }//end if
                break;
            default:
                break;
        }
    }

}
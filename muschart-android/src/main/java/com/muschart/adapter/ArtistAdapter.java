package com.muschart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.net.UrlEscapers;
import com.muschart.R;
import com.muschart.constants.UrlConstants;
import com.muschart.entity.ArtistEntity;
import com.muschart.listener.ContentListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistAdapter extends BaseAdapter {

    private Context context;
    private List<ArtistEntity> artists;
    private ContentListener contentNavigationListener;
    private LayoutInflater lInflater;

    public ArtistAdapter(Context context, List<ArtistEntity> artists, ContentListener contentNavigationListener) {
        this.context = context;
        this.artists = artists;
        this.contentNavigationListener = contentNavigationListener;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        private LinearLayout artist;
        private TextView id, name;
        private ImageView photo;
    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public Object getItem(int position) {
        return artists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArtistAdapter.ViewHolder viewHolder;
        View view = convertView;

        if (convertView == null) {
            view = lInflater.inflate(R.layout.artist_list, parent, false);
            viewHolder = new ArtistAdapter.ViewHolder();
            viewHolder.artist = (LinearLayout) view.findViewById(R.id.artist);
            viewHolder.id = (TextView) view.findViewById(R.id.id);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.photo = (ImageView) view.findViewById(R.id.photo);
            view.setTag(viewHolder);
        } else
            viewHolder = (ArtistAdapter.ViewHolder) view.getTag();

        viewHolder.id.setTag(position);
        viewHolder.name.setTag(position);
        viewHolder.photo.setTag(position);

        ArtistEntity artist = artists.get(position);
        viewHolder.artist.setOnClickListener(v -> contentNavigationListener.navigateToArtistTracks(artist.getId()));
        viewHolder.id.setText(String.valueOf(position + 1));
        viewHolder.name.setText(artist.getName());
        Picasso.with(context).load(UrlEscapers.urlFragmentEscaper().escape(UrlConstants.MetadataUrlConstants.ARTIST_IMAGE_METADATA + "/" + artist.getPhoto())).into(viewHolder.photo);

        return view;
    }

}
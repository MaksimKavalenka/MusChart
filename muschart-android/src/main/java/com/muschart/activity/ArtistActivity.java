package com.muschart.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.muschart.R;
import com.muschart.rest.ArtistServiceRestClient;

public class ArtistActivity extends Fragment {

    private ArtistServiceRestClient artistServiceRestClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list, container, false);
        ListView artistList = (ListView) view.findViewById(R.id.contentList);
        artistServiceRestClient = new ArtistServiceRestClient(view.getContext(), artistList);
        artistServiceRestClient.getArtists(1, false, 1);
        return view;
    }

}
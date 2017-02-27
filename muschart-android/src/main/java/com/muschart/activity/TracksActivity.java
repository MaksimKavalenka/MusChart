package com.muschart.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.muschart.R;
import com.muschart.rest.TrackServiceRestClient;

public class TracksActivity extends Fragment {

    private ListView trackList;
    private TrackServiceRestClient trackServiceRestClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_tracks, container, false);
        trackList = (ListView) view.findViewById(R.id.trackList);
        trackServiceRestClient = new TrackServiceRestClient(view.getContext().getApplicationContext(), trackList);
        trackServiceRestClient.getTracks(1, false, 1);
        return view;
    }

}
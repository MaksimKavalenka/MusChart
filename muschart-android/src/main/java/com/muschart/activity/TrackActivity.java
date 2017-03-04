package com.muschart.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.muschart.R;
import com.muschart.rest.TrackServiceRestClient;

public class TrackActivity extends Fragment {

    private TrackServiceRestClient trackServiceRestClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list, container, false);
        ListView trackList = (ListView) view.findViewById(R.id.contentList);
        ListView pageList = (ListView) view.findViewById(R.id.pageList);
        trackServiceRestClient = new TrackServiceRestClient(view.getContext(), trackList, pageList);
        trackServiceRestClient.getTracks(1, false, 1);
        trackServiceRestClient.getPagesCount();
        return view;
    }

}
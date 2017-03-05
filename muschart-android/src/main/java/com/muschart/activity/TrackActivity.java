package com.muschart.activity;

import static com.muschart.constants.DefaultConstants.DEFAULT_PAGE;
import static com.muschart.system.Settings.getSort;
import static com.muschart.system.Settings.getOrder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.muschart.R;
import com.muschart.service.client.dao.TrackServiceDAO;
import com.muschart.service.client.impl.TrackServiceImpl;

public class TrackActivity extends Fragment {

    private TrackServiceDAO trackService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_data, container, false);

        ListView trackList = (ListView) view.findViewById(R.id.contentList);
        LinearLayout pageList = (LinearLayout) view.findViewById(R.id.pageList);

        trackService = new TrackServiceImpl(view.getContext(), trackList, pageList);
        trackService.getTracks(getSort(), getOrder(), DEFAULT_PAGE);
        trackService.getPagesCount();

        return view;
    }

}
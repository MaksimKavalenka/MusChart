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
import com.muschart.service.client.dao.ArtistServiceDAO;
import com.muschart.service.client.impl.ArtistServiceImpl;

public class ArtistActivity extends Fragment {

    private ArtistServiceDAO artistService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_data, container, false);

        ListView artistList = (ListView) view.findViewById(R.id.contentList);
        LinearLayout pageList = (LinearLayout) view.findViewById(R.id.pageList);

        artistService = new ArtistServiceImpl(view.getContext(), artistList, pageList);
        artistService.getArtists(getSort(), getOrder(), DEFAULT_PAGE);
        artistService.getPagesCount();

        return view;
    }

}
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
import com.muschart.service.client.dao.GenreServiceDAO;
import com.muschart.service.client.impl.GenreServiceImpl;

public class GenreActivity extends Fragment {

    private GenreServiceDAO genreService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_data, container, false);

        ListView genreList = (ListView) view.findViewById(R.id.contentList);
        LinearLayout pageList = (LinearLayout) view.findViewById(R.id.pageList);

        genreService = new GenreServiceImpl(view.getContext(), genreList, pageList);
        genreService.getGenres(getSort(), getOrder(), DEFAULT_PAGE);
        genreService.getPagesCount();

        return view;
    }

}
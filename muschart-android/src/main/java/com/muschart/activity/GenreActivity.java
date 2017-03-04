package com.muschart.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.muschart.R;
import com.muschart.rest.GenreServiceRestClient;

public class GenreActivity extends Fragment {

    private GenreServiceRestClient genreServiceRestClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_list, container, false);
        ListView genreList = (ListView) view.findViewById(R.id.contentList);
        genreServiceRestClient = new GenreServiceRestClient(view.getContext(), genreList);
        genreServiceRestClient.getGenres(1, false, 1);
        return view;
    }

}
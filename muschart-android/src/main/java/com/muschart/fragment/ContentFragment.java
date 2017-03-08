package com.muschart.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.muschart.R;
import com.muschart.listener.ContentNavigationListener;
import com.muschart.listener.EventListener;
import com.muschart.service.client.dao.ArtistServiceDAO;
import com.muschart.service.client.dao.GenreServiceDAO;
import com.muschart.service.client.dao.TrackServiceDAO;
import com.muschart.service.client.impl.ArtistServiceImpl;
import com.muschart.service.client.impl.GenreServiceImpl;
import com.muschart.service.client.impl.TrackServiceImpl;

import static com.muschart.constants.DefaultConstants.DEFAULT_PAGE;
import static com.muschart.system.Settings.getOrder;
import static com.muschart.system.Settings.getSort;

@SuppressLint("ValidFragment")
public class ContentFragment extends Fragment implements ContentNavigationListener {

    private EventListener eventListener;
    private ArtistServiceDAO artistService;
    private GenreServiceDAO genreService;
    private TrackServiceDAO trackService;

    public ContentFragment(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_data, container, false);

        ListView trackList = (ListView) view.findViewById(R.id.contentList);
        LinearLayout pageList = (LinearLayout) view.findViewById(R.id.pageList);

        artistService = new ArtistServiceImpl(view.getContext(), trackList, pageList);
        genreService = new GenreServiceImpl(view.getContext(), trackList, pageList);
        trackService = new TrackServiceImpl(view.getContext(), trackList, pageList);

        eventListener.onContentFragmentAvailable();
        return view;
    }

    @Override
    public void navigateToArtists() {
        artistService.getArtists(getSort(), getOrder(), DEFAULT_PAGE);
        artistService.getPagesCount();
    }

    @Override
    public void navigateToGenres() {
        genreService.getGenres(getSort(), getOrder(), DEFAULT_PAGE);
        genreService.getPagesCount();
    }

    @Override
    public void navigateToTracks() {
        trackService.getTracks(getSort(), getOrder(), DEFAULT_PAGE);
        trackService.getPagesCount();
    }

    @Override
    public void navigateToUserArtists() {
        artistService.getUserArtists(getSort(), getOrder(), DEFAULT_PAGE);
        artistService.getUserPagesCount();
    }

    @Override
    public void navigateToUserGenres() {
        genreService.getUserGenres(getSort(), getOrder(), DEFAULT_PAGE);
        genreService.getUserPagesCount();
    }

    @Override
    public void navigateToUserTracks() {
        trackService.getUserTracks(getSort(), getOrder(), DEFAULT_PAGE);
        trackService.getUserPagesCount();
    }

}
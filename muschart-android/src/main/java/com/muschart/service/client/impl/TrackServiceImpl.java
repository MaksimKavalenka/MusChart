package com.muschart.service.client.impl;

import static com.muschart.constants.JSONEntityConstants.JSONArtistFields;
import static com.muschart.constants.JSONEntityConstants.JSONTrackFields.*;
import static com.muschart.constants.JSONEntityConstants.JSONUnitFields;
import static com.muschart.constants.UrlConstants.ServiceUrlConstants.TRACK_SERVICE;
import static com.muschart.system.Settings.getSort;
import static com.muschart.system.Settings.getOrder;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.muschart.adapter.TrackAdapter;
import com.muschart.entity.ArtistEntity;
import com.muschart.entity.TrackEntity;
import com.muschart.service.client.RestClient;
import com.muschart.service.client.dao.TrackServiceDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TrackServiceImpl implements TrackServiceDAO {

    private static final String LOG_TAG = "TrackServiceImpl";

    private TrackServiceDAO self;
    private Context context;
    private ListView trackList;
    private LinearLayout pageList;

    public TrackServiceImpl(Context context, ListView trackList, LinearLayout pageList) {
        self = this;
        this.context = context;
        this.trackList = trackList;
        this.pageList = pageList;
    }

    @Override
    public void getTracks(int sort, boolean order, int page) {
        RestClient.get(TRACK_SERVICE + "/" + sort + "/" + order + "/" + page, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    List<TrackEntity> tracks = new ArrayList<>(response.length());
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonTrack = response.getJSONObject(i);
                        TrackEntity track = new TrackEntity();
                        track.setId(jsonTrack.getLong(ID));
                        track.setName(jsonTrack.getString(NAME));
                        track.setSong(jsonTrack.getString(SONG));
                        track.setCover(jsonTrack.getString(COVER));
                        track.setVideo(jsonTrack.getString(VIDEO));
                        track.setRating(jsonTrack.getLong(RATING));

                        JSONArray artists = jsonTrack.getJSONArray(ARTISTS);
                        for (int j = 0; j < artists.length(); j++) {
                            JSONObject jsonArtist = artists.getJSONObject(j);
                            ArtistEntity artist = new ArtistEntity();
                            artist.setId(jsonArtist.getLong(JSONArtistFields.ID));
                            artist.setName(jsonArtist.getString(JSONArtistFields.NAME));
                            track.getArtists().add(artist);
                        }

                        JSONArray units = jsonTrack.getJSONArray(UNITS);
                        for (int j = 0; j < units.length(); j++) {
                            JSONObject jsonUnit = units.getJSONObject(j);
                            track.getUnits().add(jsonUnit.getString(JSONUnitFields.NAME));
                        }

                        tracks.add(track);
                    }

                    TrackAdapter adapter = new TrackAdapter(context, tracks);
                    trackList.setAdapter(adapter);
                } catch (JSONException e) {
                    AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONArray)", e);
                }
            }

        });
    }

    @Override
    public void getPagesCount() {
        RestClient.get(TRACK_SERVICE + "/pages_count", null, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                int pagesCount = Integer.valueOf(responseString);
                int fromPage = 1;
                int toPage = 5;
                if (pagesCount <= 5) {
                    toPage = pagesCount;
                } else {
                    fromPage = pagesCount - 4;
                }
                for (int i = fromPage; i <= toPage; i++) {
                    int page = i;
                    Button buttonPage = new Button(context);
                    buttonPage.setId(i);
                    buttonPage.setText(String.valueOf(i));
                    buttonPage.setOnClickListener(view -> self.getTracks(getSort(), getOrder(), page));
                    pageList.addView(buttonPage);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AsyncHttpClient.log.w(LOG_TAG, "onFailure(int, Header[], String, Throwable)", throwable);
            }

        });
    }

}
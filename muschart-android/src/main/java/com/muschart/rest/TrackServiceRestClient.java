package com.muschart.rest;

import static com.muschart.constants.JSONEntityConstants.JSONArtistFields;
import static com.muschart.constants.JSONEntityConstants.JSONTrackFields.*;
import static com.muschart.constants.JSONEntityConstants.JSONUnitFields;

import android.content.Context;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.muschart.adapter.PageAdapter;
import com.muschart.adapter.TrackAdapter;
import com.muschart.constants.UrlConstants;
import com.muschart.entity.ArtistEntity;
import com.muschart.entity.TrackEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TrackServiceRestClient {

    private Context context;
    private ListView trackList;
    private ListView pageList;

    public TrackServiceRestClient(Context context, ListView trackList, ListView pageList) {
        this.context = context;
        this.trackList = trackList;
        this.pageList = pageList;
    }

    public void getTracks(int sort, boolean order, int page) {
        RestClient.get(UrlConstants.ServiceUrlConstants.TRACK_SERVICE + "/" + sort + "/" + order + "/" + page, null, new JsonHttpResponseHandler() {

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
                    //TO HANDLE
                }
            }

        });
    }

    public void getPagesCount() {
        RestClient.get(UrlConstants.ServiceUrlConstants.TRACK_SERVICE + "/pages_count", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Long> qwe = new ArrayList<Long>(2);
                qwe.add((long) 2);
                qwe.add((long) 5);
                PageAdapter adapter = new PageAdapter(context, qwe);
                pageList.setAdapter(adapter);
            }

        });
    }

}
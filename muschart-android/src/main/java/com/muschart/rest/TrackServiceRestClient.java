package com.muschart.rest;

import android.content.Context;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
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

    public TrackServiceRestClient(Context context, ListView trackList) {
        this.context = context;
        this.trackList = trackList;
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
                        track.setId(jsonTrack.getLong("id"));
                        track.setName(jsonTrack.getString("name"));
                        track.setCover(jsonTrack.getString("cover"));
                        track.setVideo(jsonTrack.getString("video"));
                        track.setRating(jsonTrack.getLong("rating"));

                        JSONArray artists = jsonTrack.getJSONArray("artists");
                        for (int j = 0; j < artists.length(); j++) {
                            JSONObject jsonArtist = artists.getJSONObject(j);
                            ArtistEntity artist = new ArtistEntity();
                            artist.setId(jsonArtist.getLong("id"));
                            artist.setName(jsonArtist.getString("name"));
                            track.getArtists().add(artist);
                        }

                        JSONArray units = jsonTrack.getJSONArray("units");
                        for (int j = 0; j < units.length(); j++) {
                            JSONObject jsonUnit = units.getJSONObject(j);
                            track.getUnits().add(jsonUnit.getString("name"));
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

}
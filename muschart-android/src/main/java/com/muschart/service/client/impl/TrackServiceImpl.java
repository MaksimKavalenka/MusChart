package com.muschart.service.client.impl;

import static com.muschart.constants.UrlConstants.ServiceUrlConstants.TRACK_SERVICE;
import static com.muschart.system.Settings.getSort;
import static com.muschart.system.Settings.getOrder;
import static com.muschart.utility.Parser.jsonToTracks;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.muschart.adapter.TrackAdapter;
import com.muschart.entity.TrackEntity;
import com.muschart.service.client.RestClient;
import com.muschart.service.client.dao.TrackServiceDAO;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TrackServiceImpl implements TrackServiceDAO {

    private static final String LOG_TAG = "TrackServiceImpl";

    private enum Actions {
        TRACKS, ARTIST_TRACKS, USER_TRACKS;
    }

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
        RestClient.get(TRACK_SERVICE + "/" + sort + "/" + order + "/" + page, null, getJsonHttpResponseHandler());
    }

    @Override
    public void getArtistTracks(long artistId, int sort, boolean order, int page) {
        RestClient.get(TRACK_SERVICE + "/artist/" + artistId + "/" + sort + "/" + order + "/" + page, null, getJsonHttpResponseHandler());
    }

    @Override
    public void getUserTracks(int sort, boolean order, int page) {
        RestClient.get(TRACK_SERVICE + "/user/" + sort + "/" + order + "/" + page, null, getJsonHttpResponseHandler());
    }

    @Override
    public void getPagesCount() {
        RestClient.get(TRACK_SERVICE + "/pages_count", null, getTextHttpResponseHandler(Actions.TRACKS, 0));
    }

    @Override
    public void getArtistTracksPagesCount(long artistId) {
        RestClient.get(TRACK_SERVICE + "/artist/" + artistId + "/pages_count", null, getTextHttpResponseHandler(Actions.ARTIST_TRACKS, artistId));
    }

    @Override
    public void getUserPagesCount() {
        RestClient.get(TRACK_SERVICE + "/user/pages_count", null, getTextHttpResponseHandler(Actions.USER_TRACKS, 0));
    }

    private JsonHttpResponseHandler getJsonHttpResponseHandler() {
        return new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    List<TrackEntity> tracks = jsonToTracks(response);
                    TrackAdapter adapter = new TrackAdapter(context, tracks);
                    trackList.setAdapter(adapter);
                } catch (JSONException e) {
                    AsyncHttpClient.log.w(LOG_TAG, "setTracks.onSuccess(int, Header[], JSONArray)", e);
                }
            }

        };
    }

    private TextHttpResponseHandler getTextHttpResponseHandler(Actions action, long entityId) {
        return new TextHttpResponseHandler() {

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

                pageList.removeAllViews();
                for (int i = fromPage; i <= toPage; i++) {
                    int page = i;
                    Button buttonPage = new Button(context);
                    buttonPage.setId(page);
                    buttonPage.setText(String.valueOf(page));
                    buttonPage.setOnClickListener(getClickListener(action, page, entityId));
                    pageList.addView(buttonPage);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AsyncHttpClient.log.w(LOG_TAG, "getTextHttpResponseHandler.onFailure(int, Header[], String, Throwable)", throwable);
            }

        };
    }

    private View.OnClickListener getClickListener(Actions action, int page, long entityId) {
        switch (action) {
            case TRACKS:
                return view -> self.getTracks(getSort(), getOrder(), page);
            case ARTIST_TRACKS:
                return view -> self.getArtistTracks(entityId, getSort(), getOrder(), page);
            case USER_TRACKS:
                return view -> self.getUserTracks(getSort(), getOrder(), page);
            default:
                return null;
        }
    }

}
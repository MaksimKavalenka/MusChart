package com.muschart.service.client.impl;

import static com.muschart.constants.UrlConstants.ServiceUrlConstants.ARTIST_SERVICE;
import static com.muschart.system.Settings.getSort;
import static com.muschart.system.Settings.getOrder;
import static com.muschart.utility.Parser.jsonToArtists;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.muschart.adapter.ArtistAdapter;
import com.muschart.entity.ArtistEntity;
import com.muschart.service.client.RestClient;
import com.muschart.service.client.dao.ArtistServiceDAO;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ArtistServiceImpl implements ArtistServiceDAO {

    private static final String LOG_TAG = "ArtistServiceImpl";

    private ArtistServiceDAO self;
    private Context context;
    private ListView artistList;
    private LinearLayout pageList;

    public ArtistServiceImpl(Context context, ListView artistList, LinearLayout pageList) {
        self = this;
        this.context = context;
        this.artistList = artistList;
        this.pageList = pageList;
    }

    @Override
    public void getArtists(int sort, boolean order, int page) {
        RestClient.get(ARTIST_SERVICE + "/" + sort + "/" + order + "/" + page, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    setArtists(response);
                } catch (JSONException e) {
                    AsyncHttpClient.log.w(LOG_TAG, "getArtists.onSuccess(int, Header[], JSONArray)", e);
                }
            }

        });
    }

    @Override
    public void getUserArtists(int sort, boolean order, int page) {
        RestClient.get(ARTIST_SERVICE + "/user/" + sort + "/" + order + "/" + page, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    setArtists(response);
                } catch (JSONException e) {
                    AsyncHttpClient.log.w(LOG_TAG, "getUserArtists.onSuccess(int, Header[], JSONArray)", e);
                }
            }

        });
    }

    @Override
    public void getPagesCount() {
        RestClient.get(ARTIST_SERVICE + "/pages_count", null, new TextHttpResponseHandler() {

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
                    buttonPage.setId(i);
                    buttonPage.setText(String.valueOf(i));
                    buttonPage.setOnClickListener(view -> self.getArtists(getSort(), getOrder(), page));
                    pageList.addView(buttonPage);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AsyncHttpClient.log.w(LOG_TAG, "getPagesCount.onFailure(int, Header[], String, Throwable)", throwable);
            }

        });
    }

    @Override
    public void getUserPagesCount() {
        RestClient.get(ARTIST_SERVICE + "/user/pages_count", null, new TextHttpResponseHandler() {

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
                    buttonPage.setId(i);
                    buttonPage.setText(String.valueOf(i));
                    buttonPage.setOnClickListener(view -> self.getUserArtists(getSort(), getOrder(), page));
                    pageList.addView(buttonPage);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AsyncHttpClient.log.w(LOG_TAG, "getUserPagesCount.onFailure(int, Header[], String, Throwable)", throwable);
            }

        });
    }

    private void setArtists(JSONArray response) throws JSONException {
        List<ArtistEntity> artists = jsonToArtists(response);
        ArtistAdapter adapter = new ArtistAdapter(context, artists);
        artistList.setAdapter(adapter);
    }

}
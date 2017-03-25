package com.muschart.service.client.impl;

import static com.muschart.constants.UrlConstants.ServiceUrlConstants.ARTIST_SERVICE;
import static com.muschart.system.Settings.getSort;
import static com.muschart.system.Settings.getOrder;
import static com.muschart.utility.Parser.jsonToArtists;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.muschart.adapter.ArtistAdapter;
import com.muschart.entity.ArtistEntity;
import com.muschart.listener.ContentListener;
import com.muschart.service.client.RestClient;
import com.muschart.service.client.dao.ArtistServiceDAO;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ArtistServiceImpl implements ArtistServiceDAO {

    private static final String LOG_TAG = "ArtistServiceImpl";

    private enum Actions {
        ARTISTS, GENRE_ARTISTS, USER_ARTISTS;
    }

    private ArtistServiceDAO self;
    private Context context;
    private ListView artistList;
    private LinearLayout pageList;
    private ContentListener contentNavigationListener;

    public ArtistServiceImpl(Context context, ListView artistList, LinearLayout pageList, ContentListener contentNavigationListener) {
        self = this;
        this.context = context;
        this.artistList = artistList;
        this.pageList = pageList;
        this.contentNavigationListener = contentNavigationListener;
    }

    @Override
    public void getArtists(int sort, boolean order, int page) {
        RestClient.get(ARTIST_SERVICE + "/" + sort + "/" + order + "/" + page, null, getJsonHttpResponseHandler());
    }

    @Override
    public void getGenreArtists(long genreId, int sort, boolean order, int page) {
        RestClient.get(ARTIST_SERVICE + "/genre/" + genreId + "/" + sort + "/" + order + "/" + page, null, getJsonHttpResponseHandler());
    }

    @Override
    public void getUserArtists(int sort, boolean order, int page) {
        RestClient.get(ARTIST_SERVICE + "/user/" + sort + "/" + order + "/" + page, null, getJsonHttpResponseHandler());
    }

    @Override
    public void getPagesCount() {
        RestClient.get(ARTIST_SERVICE + "/pages_count", null, getTextHttpResponseHandler(Actions.ARTISTS, 0));
    }

    @Override
    public void getGenreArtistsPagesCount(long genreId) {
        RestClient.get(ARTIST_SERVICE + "/genre/" + genreId + "/pages_count", null, getTextHttpResponseHandler(Actions.GENRE_ARTISTS, genreId));
    }

    @Override
    public void getUserPagesCount() {
        RestClient.get(ARTIST_SERVICE + "/user/pages_count", null, getTextHttpResponseHandler(Actions.USER_ARTISTS, 0));
    }

    private JsonHttpResponseHandler getJsonHttpResponseHandler() {
        return new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    List<ArtistEntity> artists = jsonToArtists(response);
                    ArtistAdapter adapter = new ArtistAdapter(context, artists, contentNavigationListener);
                    artistList.setAdapter(adapter);
                } catch (JSONException e) {
                    AsyncHttpClient.log.w(LOG_TAG, "getJsonHttpResponseHandler.onSuccess(int, Header[], JSONArray)", e);
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
            case ARTISTS:
                return view -> self.getArtists(getSort(), getOrder(), page);
            case GENRE_ARTISTS:
                return view -> self.getGenreArtists(entityId, getSort(), getOrder(), page);
            case USER_ARTISTS:
                return view -> self.getUserArtists(getSort(), getOrder(), page);
            default:
                return null;
        }
    }

}
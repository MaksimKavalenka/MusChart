package com.muschart.service.client.impl;

import static com.muschart.constants.JSONEntityConstants.JSONGenreFields.*;
import static com.muschart.constants.UrlConstants.ServiceUrlConstants.GENRE_SERVICE;
import static com.muschart.system.Settings.getSort;
import static com.muschart.system.Settings.getOrder;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.muschart.adapter.GenreAdapter;
import com.muschart.entity.GenreEntity;
import com.muschart.service.client.RestClient;
import com.muschart.service.client.dao.GenreServiceDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GenreServiceImpl implements GenreServiceDAO {

    private static final String LOG_TAG = "GenreServiceImpl";

    private GenreServiceDAO self;
    private Context context;
    private ListView genreList;
    private LinearLayout pageList;

    public GenreServiceImpl(Context context, ListView genreList, LinearLayout pageList) {
        self = this;
        this.context = context;
        this.genreList = genreList;
        this.pageList = pageList;
    }

    @Override
    public void getGenres(int sort, boolean order, int page) {
        RestClient.get(GENRE_SERVICE + "/" + sort + "/" + order + "/" + page, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    List<GenreEntity> genres = new ArrayList<>(response.length());
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonGenre = response.getJSONObject(i);
                        GenreEntity genre = new GenreEntity();
                        genre.setId(jsonGenre.getLong(ID));
                        genre.setName(jsonGenre.getString(NAME));
                        genre.setRating(jsonGenre.getLong(RATING));
                        genres.add(genre);
                    }

                    GenreAdapter adapter = new GenreAdapter(context, genres);
                    genreList.setAdapter(adapter);
                } catch (JSONException e) {
                    AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONArray)", e);
                }
            }

        });
    }

    @Override
    public void getPagesCount() {
        RestClient.get(GENRE_SERVICE + "/pages_count", null, new TextHttpResponseHandler() {

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
                    buttonPage.setOnClickListener(view -> self.getGenres(getSort(), getOrder(), page));
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
package com.muschart.rest;

import android.content.Context;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.muschart.adapter.ArtistAdapter;
import com.muschart.adapter.GenreAdapter;
import com.muschart.constants.UrlConstants;
import com.muschart.entity.ArtistEntity;
import com.muschart.entity.GenreEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.muschart.constants.JSONEntityConstants.JSONArtistFields.ID;
import static com.muschart.constants.JSONEntityConstants.JSONArtistFields.NAME;
import static com.muschart.constants.JSONEntityConstants.JSONArtistFields.PHOTO;
import static com.muschart.constants.JSONEntityConstants.JSONArtistFields.RATING;

public class GenreServiceRestClient {

    private Context context;
    private ListView genreList;

    public GenreServiceRestClient(Context context, ListView genreList) {
        this.context = context;
        this.genreList = genreList;
    }

    public void getGenres(int sort, boolean order, int page) {
        RestClient.get(UrlConstants.ServiceUrlConstants.GENRE_SERVICE + "/" + sort + "/" + order + "/" + page, null, new JsonHttpResponseHandler() {

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
                    //TO HANDLE
                }
            }

        });
    }

}
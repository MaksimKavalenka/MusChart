package com.muschart.rest;

import static com.muschart.constants.JSONEntityConstants.JSONArtistFields.*;

import android.content.Context;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.muschart.adapter.ArtistAdapter;
import com.muschart.constants.UrlConstants;
import com.muschart.entity.ArtistEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ArtistServiceRestClient {

    private Context context;
    private ListView artistList;

    public ArtistServiceRestClient(Context context, ListView artistList) {
        this.context = context;
        this.artistList = artistList;
    }

    public void getArtists(int sort, boolean order, int page) {
        RestClient.get(UrlConstants.ServiceUrlConstants.ARTIST_SERVICE + "/" + sort + "/" + order + "/" + page, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    List<ArtistEntity> artists = new ArrayList<>(response.length());
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonArtist = response.getJSONObject(i);
                        ArtistEntity artist = new ArtistEntity();
                        artist.setId(jsonArtist.getLong(ID));
                        artist.setName(jsonArtist.getString(NAME));
                        artist.setPhoto(jsonArtist.getString(PHOTO));
                        artist.setRating(jsonArtist.getLong(RATING));
                        artists.add(artist);
                    }

                    ArtistAdapter adapter = new ArtistAdapter(context, artists);
                    artistList.setAdapter(adapter);
                } catch (JSONException e) {
                    //TO HANDLE
                }
            }

        });
    }

}
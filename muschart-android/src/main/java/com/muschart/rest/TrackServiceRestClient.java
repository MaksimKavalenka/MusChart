package com.muschart.rest;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.muschart.R;
import com.muschart.constants.ServiceConstants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TrackServiceRestClient {

    public static void getTracks(int sort, boolean order, int page, final Context context, final ListView trackList) {
        RestClient.get(ServiceConstants.TRACK_SERVICE + "/" + sort + "/" + order + "/" + page, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    List<String> tracks = new ArrayList<>(response.length());
                    for (int i = 0; i < response.length(); i++) {
                        tracks.add(response.getJSONObject(i).getString("name"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.track_list_item, tracks);
                    trackList.setAdapter(adapter);
                } catch (JSONException e) {
                    //TO HANDLE
                }
            }

        });
    }

}
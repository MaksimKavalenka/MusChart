package com.muschart.rest;

import android.util.Base64;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.muschart.constants.ServiceConstants;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UserServiceRestClient {

    public static void authentication(String login, String password, final EditText text) {
        String userpass = login + ":" + password;
        String encoded = new String(Base64.encode(userpass.getBytes(), Base64.NO_WRAP));

        RestClient.getClient().addHeader("Authorization", "Basic " + encoded);
        RestClient.get(ServiceConstants.USER_SERVICE + "/auth", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                text.setText(response.toString());
            }

        });
    }

}
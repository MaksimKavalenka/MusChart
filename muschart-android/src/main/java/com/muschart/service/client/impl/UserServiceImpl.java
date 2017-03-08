package com.muschart.service.client.impl;

import static com.muschart.constants.JSONEntityConstants.JSONUserFields.*;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.muschart.constants.UrlConstants;
import com.muschart.listener.EventListener;
import com.muschart.service.client.RestClient;
import com.muschart.service.client.dao.UserServiceDAO;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UserServiceImpl implements UserServiceDAO {

    private static final String LOG_TAG = "UserServiceImpl";

    private FragmentActivity fragmentActivity;
    private EventListener eventListener;
    private TextView userLogin;
    private TextView userEmail;

    public UserServiceImpl(FragmentActivity fragmentActivity, EventListener eventListener, TextView userLogin, TextView userEmail) {
        this.fragmentActivity = fragmentActivity;
        this.eventListener = eventListener;
        this.userLogin = userLogin;
        this.userEmail = userEmail;
    }

    @Override
    public void authentication(String login, String password) {
        String userpass = login + ":" + password;
        String encoded = new String(Base64.encode(userpass.getBytes(), Base64.NO_WRAP));

        RestClient.getClient().addHeader("Authorization", "Basic " + encoded);
        RestClient.get(UrlConstants.ServiceUrlConstants.USER_SERVICE + "/auth", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    View view = fragmentActivity.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    userLogin.setText(response.getString(LOGIN));
                    eventListener.onLogin();
                } catch (JSONException e) {
                    AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject)", e);
                }
            }

        });
    }

}
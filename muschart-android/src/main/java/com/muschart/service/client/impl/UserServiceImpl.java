package com.muschart.service.client.impl;

import static com.muschart.constants.JSONEntityConstants.JSONUserFields.*;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.muschart.R;
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
    public void authentication(EditText loginField, EditText passwordField, TextView messageField) {
        String login = loginField.getText().toString();
        String password = passwordField.getText().toString();
        String encoded = new String(Base64.encode((login + ":" + password).getBytes(), Base64.NO_WRAP));

        RestClient.getClient().addHeader("Authorization", "Basic " + encoded);
        RestClient.get(UrlConstants.ServiceUrlConstants.USER_SERVICE + "/auth", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //try {
                    View view = fragmentActivity.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    loginField.getText().clear();
                    passwordField.getText().clear();
                    //userLogin.setText(response.getString(LOGIN));
                    messageField.setVisibility(View.GONE);
                    messageField.setText("");
                    eventListener.onLogin();
                //} catch (JSONException e) {
                    //AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject)", e);
                //}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                messageField.setText(fragmentActivity.getString(R.string.message_authentication_error));
                messageField.setVisibility(View.VISIBLE);
            }

        });
    }

    @Override
    public void logout() {
        RestClient.post(UrlConstants.ServiceUrlConstants.USER_SERVICE + "/logout", null, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //userLogin.setText("");
                //userEmail.setText("");
                eventListener.onLogout();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                eventListener.onLogout();
            }

        });
    }

    @Override
    public void register(EditText loginField, EditText passwordField, EditText confirmPasswordField, TextView messageField) {
        String login = loginField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();

        if (!password.equals(confirmPassword)) {
            messageField.setVisibility(View.VISIBLE);
            messageField.setText(fragmentActivity.getString(R.string.passwords_do_not_match));
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.add("login", login);
        requestParams.add("password", password);
        requestParams.add("confirmPassword", confirmPassword);

        RestClient.post(UrlConstants.ServiceUrlConstants.USER_SERVICE, requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //try {
                View view = fragmentActivity.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                loginField.getText().clear();
                passwordField.getText().clear();
                confirmPasswordField.getText().clear();
                //userLogin.setText(response.getString(LOGIN));
                messageField.setVisibility(View.GONE);
                messageField.setText("");
                eventListener.onLogin();
                //} catch (JSONException e) {
                //AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject)", e);
                //}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
            }

        });
    }

}
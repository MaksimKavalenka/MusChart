package com.muschart.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.muschart.R;
import com.muschart.rest.UserServiceRestClient;

public class LoginActivity extends Fragment {

    private EditText loginField, passwordField;
    private Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_login, container, false);
        loginField = (EditText) view.findViewById(R.id.login_login);
        passwordField = (EditText) view.findViewById(R.id.login_password);
        login = (Button) view.findViewById(R.id.login);
        login.setOnClickListener(getLoginClickListener());
        return view;
    }

    private View.OnClickListener getLoginClickListener() {
        View.OnClickListener loginClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserServiceRestClient.authentication(loginField.getText().toString(), passwordField.getText().toString(), loginField);
            }
        };
        return loginClickListener;
    }

}
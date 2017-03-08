package com.muschart.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.muschart.R;
import com.muschart.listener.EventListener;
import com.muschart.service.client.dao.UserServiceDAO;
import com.muschart.service.client.impl.UserServiceImpl;

@SuppressLint("ValidFragment")
public class LoginFragment extends Fragment {

    private EventListener eventListener;
    private UserServiceDAO userService;

    public LoginFragment(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_login, container, false);

        TextView userLogin = (TextView) getActivity().findViewById(R.id.user_login);
        TextView userEmail = (TextView) getActivity().findViewById(R.id.user_email);

        userService = new UserServiceImpl(getActivity(), eventListener, userLogin, userEmail);

        EditText loginField = (EditText) view.findViewById(R.id.text_login);
        EditText passwordField = (EditText) view.findViewById(R.id.text_password);
        Button login = (Button) view.findViewById(R.id.login);

        eventListener.onLoginFragmentAvailable();
        login.setOnClickListener(viewListener -> userService.authentication(loginField.getText().toString(), passwordField.getText().toString()));

        return view;
    }

}
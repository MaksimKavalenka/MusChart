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
import com.muschart.listener.LoginListener;
import com.muschart.service.client.dao.UserServiceDAO;
import com.muschart.service.client.impl.UserServiceImpl;

@SuppressLint("ValidFragment")
public class LoginFragment extends Fragment implements LoginListener {

    private EventListener eventListener;
    private UserServiceDAO userService;
    private EditText loginField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private TextView messageField;
    private Button buttonLogin;

    public LoginFragment(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_login, container, false);

        TextView userLogin = (TextView) getActivity().findViewById(R.id.user_login);
        TextView userEmail = (TextView) getActivity().findViewById(R.id.user_email);

        userService = new UserServiceImpl(getActivity(), eventListener, userLogin, userEmail);

        loginField = (EditText) view.findViewById(R.id.text_login);
        passwordField = (EditText) view.findViewById(R.id.text_password);
        confirmPasswordField = (EditText) view.findViewById(R.id.text_confirm_password);
        messageField = (TextView) view.findViewById(R.id.text_message);
        buttonLogin = (Button) view.findViewById(R.id.button_login);

        eventListener.onLoginFragmentAvailable();

        return view;
    }

    @Override
    public void navigateToLogin() {
        reset();
        confirmPasswordField.setVisibility(View.GONE);
        buttonLogin.setText(getActivity().getString(R.string.log_in));
        buttonLogin.setOnClickListener(viewListener -> userService.authentication(loginField, passwordField, messageField));
    }

    @Override
    public void navigateToLogout() {
        userService.logout();
    }

    @Override
    public void navigateToRegister() {
        reset();
        confirmPasswordField.setVisibility(View.VISIBLE);
        buttonLogin.setText(getActivity().getString(R.string.register));
        buttonLogin.setOnClickListener(viewListener -> userService.register(loginField, passwordField, confirmPasswordField, messageField));
    }

    private void reset() {
        loginField.getText().clear();
        passwordField.getText().clear();
        confirmPasswordField.getText().clear();
        messageField.setVisibility(View.GONE);
        messageField.setText("");
    }

}
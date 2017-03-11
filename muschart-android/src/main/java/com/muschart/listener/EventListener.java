package com.muschart.listener;

public interface EventListener {

    void onLogin();

    void onLogout();

    void onContentFragmentAvailable();

    void onLoginFragmentAvailable();

}
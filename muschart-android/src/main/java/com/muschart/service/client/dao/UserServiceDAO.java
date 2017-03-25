package com.muschart.service.client.dao;

import android.widget.EditText;
import android.widget.TextView;

public interface UserServiceDAO {

    void authentication(EditText loginField, EditText passwordField, TextView messaegField);

    void logout();

    void register(EditText loginField, EditText passwordField, EditText confirmPasswordField,TextView messaegField);

}
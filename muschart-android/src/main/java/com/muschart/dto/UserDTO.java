package com.muschart.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private String login;
    private String password;
    private String confirmPassword;

    public UserDTO() {
    }

    public UserDTO(String login, String password, String confirmPassword) {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[login:" + login + ",password:" + password + ",confirmPassword:" + confirmPassword + "]";
    }

}
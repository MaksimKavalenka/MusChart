package com.muschart.dto;

import android.support.v4.app.Fragment;

import java.io.Serializable;

public class FragmentDTO implements Serializable {

    private Fragment fragment;
    private boolean isAvailable;

    public FragmentDTO() {
    }

    public FragmentDTO(Fragment fragment, boolean isAvailable) {
        this.fragment = fragment;
        this.isAvailable = isAvailable;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[isAvailable:" + isAvailable + "]";
    }

}
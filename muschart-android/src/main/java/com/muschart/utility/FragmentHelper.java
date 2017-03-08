package com.muschart.utility;

import static com.muschart.constants.FragmentConstants.*;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.muschart.R;
import com.muschart.fragment.ContentFragment;
import com.muschart.fragment.LoginFragment;
import com.muschart.dto.FragmentDTO;
import com.muschart.listener.ContentNavigationListener;
import com.muschart.listener.EventListener;

import java.util.HashMap;
import java.util.Map;

public class FragmentHelper implements EventListener {

    private AppCompatActivity appCompatActivity;
    private Context context;

    private Map<String, FragmentDTO> fragments;
    private int currentViewId;

    public FragmentHelper(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        context = appCompatActivity.getApplicationContext();
        fragments = new HashMap<>(2);
        currentViewId = R.id.nav_tracks;
    }

    public int getCurrentViewId() {
        return currentViewId;
    }

    public void setCurrentViewId(int currentViewId) {
        this.currentViewId = currentViewId;
    }

    public void displayView(int viewId) {
        Fragment fragment = null;
        String title = context.getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_log_in:
                title = context.getString(R.string.log_in);
                break;
            case R.id.nav_log_out:
                title = context.getString(R.string.log_out);
                break;
            case R.id.nav_register:
                title = context.getString(R.string.register);
                break;
            case R.id.nav_artists:
                title = context.getString(R.string.artists);
                break;
            case R.id.nav_genres:
                title = context.getString(R.string.genres);
                break;
            case R.id.nav_tracks:
                title = context.getString(R.string.tracks);
                break;
            case R.id.nav_my_artists:
                title = context.getString(R.string.my_artists);
                break;
            case R.id.nav_my_genres:
                title = context.getString(R.string.my_genres);
                break;
            case R.id.nav_my_tracks:
                title = context.getString(R.string.my_tracks);
                break;
            case R.id.nav_settings:
                title = context.getString(R.string.action_settings);
                break;
        }

        switch (viewId) {
            case R.id.nav_log_in:
                fragment = getFragment(LOGIN_FRAGMENT).getFragment();
                break;
            case R.id.nav_artists:
            case R.id.nav_genres:
            case R.id.nav_tracks:
            case R.id.nav_my_artists:
            case R.id.nav_my_genres:
            case R.id.nav_my_tracks:
                fragment = getFragment(CONTENT_FRAGMENT).getFragment();
                changeContent();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) appCompatActivity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void changeContent() {
        if (getFragment(CONTENT_FRAGMENT).isAvailable()) {
            Fragment fragment = getFragment(CONTENT_FRAGMENT).getFragment();

            switch (currentViewId) {
                case R.id.nav_artists:
                    ((ContentNavigationListener) fragment).navigateToArtists();
                    break;
                case R.id.nav_genres:
                    ((ContentNavigationListener) fragment).navigateToGenres();
                    break;
                case R.id.nav_tracks:
                    ((ContentNavigationListener) fragment).navigateToTracks();
                    break;
                case R.id.nav_my_artists:
                    ((ContentNavigationListener) fragment).navigateToUserArtists();
                    break;
                case R.id.nav_my_genres:
                    ((ContentNavigationListener) fragment).navigateToUserGenres();
                    break;
                case R.id.nav_my_tracks:
                    ((ContentNavigationListener) fragment).navigateToUserTracks();
                    break;
            }
        }
    }

    private FragmentDTO getFragment(String fragmentKey) {
        FragmentDTO fragment = fragments.get(fragmentKey);
        if (fragment == null) {
            switch (fragmentKey) {
                case CONTENT_FRAGMENT:
                    fragment = new FragmentDTO(new ContentFragment(this), false);
                    fragments.put(CONTENT_FRAGMENT, fragment);
                    break;
                case LOGIN_FRAGMENT:
                    fragment = new FragmentDTO(new LoginFragment(this), false);
                    fragments.put(LOGIN_FRAGMENT, fragment);
                    break;
            }
        }
        return fragment;
    }

    private void setFragmentsAvailable(String fragmentKey) {
        for (Map.Entry<String, FragmentDTO> fragment : fragments.entrySet()) {
            if (fragment.getKey().equals(fragmentKey)) {
                fragment.getValue().setAvailable(true);
            } else {
                fragment.getValue().setAvailable(false);
            }
        }
    }

    @Override
    public void onLogin() {
        currentViewId = R.id.nav_tracks;
        displayView(currentViewId);
    }

    @Override
    public void onContentFragmentAvailable() {
        setFragmentsAvailable(CONTENT_FRAGMENT);
        changeContent();
    }

    @Override
    public void onLoginFragmentAvailable() {
        setFragmentsAvailable(LOGIN_FRAGMENT);
    }

}
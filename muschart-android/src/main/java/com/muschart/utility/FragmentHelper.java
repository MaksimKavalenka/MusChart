package com.muschart.utility;

import static com.muschart.constants.FragmentConstants.*;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.muschart.R;
import com.muschart.fragment.ContentFragment;
import com.muschart.fragment.LoginFragment;
import com.muschart.dto.FragmentDTO;
import com.muschart.listener.ContentListener;
import com.muschart.listener.EventListener;
import com.muschart.listener.LoginListener;

import java.util.HashMap;
import java.util.Map;

public class FragmentHelper implements NavigationView.OnNavigationItemSelectedListener, EventListener {

    private Menu navigationMenu;

    private AppCompatActivity appCompatActivity;
    private Context context;

    private Map<String, FragmentDTO> fragments;
    private int currentViewId;

    public FragmentHelper(AppCompatActivity appCompatActivity) {
        NavigationView navigationView = (NavigationView) appCompatActivity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationMenu = navigationView.getMenu();

        this.appCompatActivity = appCompatActivity;
        context = appCompatActivity.getApplicationContext();
        fragments = new HashMap<>(2);
        currentViewId = R.id.nav_tracks;

        setNavigationMenuVisibility(false, R.id.nav_register, R.id.nav_log_out, R.id.nav_my_artists, R.id.nav_my_genres, R.id.nav_my_tracks, R.id.nav_settings);
        displayView(R.id.nav_tracks);
    }

    private void displayView(int viewId) {
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
            case R.id.nav_register:
                fragment = getFragment(LOGIN_FRAGMENT).getFragment();
                changeLogin();
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

    private void changeLogin() {
        if (getFragment(LOGIN_FRAGMENT).isAvailable()) {
            Fragment fragment = getFragment(LOGIN_FRAGMENT).getFragment();

            switch (currentViewId) {
                case R.id.nav_log_in:
                    ((LoginListener) fragment).navigateToLogin();
                    break;
                case R.id.nav_register:
                    ((LoginListener) fragment).navigateToRegister();
                    break;
            }
        }
    }

    private void changeContent() {
        if (getFragment(CONTENT_FRAGMENT).isAvailable()) {
            Fragment fragment = getFragment(CONTENT_FRAGMENT).getFragment();

            switch (currentViewId) {
                case R.id.nav_artists:
                    ((ContentListener) fragment).navigateToArtists();
                    break;
                case R.id.nav_genres:
                    ((ContentListener) fragment).navigateToGenres();
                    break;
                case R.id.nav_tracks:
                    ((ContentListener) fragment).navigateToTracks();
                    break;
                case R.id.nav_my_artists:
                    ((ContentListener) fragment).navigateToUserArtists();
                    break;
                case R.id.nav_my_genres:
                    ((ContentListener) fragment).navigateToUserGenres();
                    break;
                case R.id.nav_my_tracks:
                    ((ContentListener) fragment).navigateToUserTracks();
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

    private void setNavigationMenuVisibility(boolean visibility, int... itemsId) {
        for (int itemId : itemsId) {
            navigationMenu.findItem(itemId).setVisible(visibility);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() != R.id.nav_log_out) {
            currentViewId = item.getItemId();
        } else {
            ((LoginListener) getFragment(LOGIN_FRAGMENT).getFragment()).navigateToLogout();
        }
        displayView(currentViewId);
        return true;
    }

    @Override
    public void onLogin() {
        setNavigationMenuVisibility(false, R.id.nav_log_in, R.id.nav_register);
        setNavigationMenuVisibility(true, R.id.nav_log_out, R.id.nav_my_artists, R.id.nav_my_genres, R.id.nav_my_tracks);
        currentViewId = R.id.nav_tracks;
        displayView(currentViewId);
    }

    @Override
    public void onLogout() {
        setNavigationMenuVisibility(false, R.id.nav_log_out, R.id.nav_my_artists, R.id.nav_my_genres, R.id.nav_my_tracks);
        setNavigationMenuVisibility(true, R.id.nav_log_in); //ADD R.id.nav_register

        if ((currentViewId == R.id.nav_my_artists) || (currentViewId == R.id.nav_my_genres) || (currentViewId == R.id.nav_my_tracks)) {
            currentViewId = R.id.nav_tracks;
            displayView(currentViewId);
        }

        ((TextView) appCompatActivity.findViewById(R.id.user_login)).clearComposingText();
        ((TextView) appCompatActivity.findViewById(R.id.user_email)).clearComposingText();
    }

    @Override
    public void onContentFragmentAvailable() {
        setFragmentsAvailable(CONTENT_FRAGMENT);
        changeContent();
    }

    @Override
    public void onLoginFragmentAvailable() {
        setFragmentsAvailable(LOGIN_FRAGMENT);
        changeLogin();
    }

}
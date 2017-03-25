package com.muschart.listener;

public interface ContentListener {

    void navigateToArtists();

    void navigateToGenres();

    void navigateToTracks();

    void navigateToArtistTracks(long artistId);

    void navigateToGenreArtists(long genreId);

    void navigateToUserArtists();

    void navigateToUserGenres();

    void navigateToUserTracks();

}
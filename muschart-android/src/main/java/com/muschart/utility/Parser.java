package com.muschart.utility;

import com.muschart.entity.TrackEntity;

public class Parser {

    public static String getFullArtistName(TrackEntity track) {
        StringBuilder artist = new StringBuilder();
        for (int i = 0; i < track.getArtists().size(); i++) {
            if (i > 0) {
                artist.append(track.getUnits().get(i - 1));
            }
            artist.append(track.getArtists().get(i).getName());
        }
        return artist.toString();
    }

}
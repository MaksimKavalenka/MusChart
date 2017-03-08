package com.muschart.utility;

import static com.muschart.constants.JSONEntityConstants.*;

import com.muschart.constants.JSONEntityConstants;
import com.muschart.entity.ArtistEntity;
import com.muschart.entity.GenreEntity;
import com.muschart.entity.TrackEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<ArtistEntity> jsonToArtists(JSONArray json) throws JSONException {
        List<ArtistEntity> artists = new ArrayList<>(json.length());
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonArtist = json.getJSONObject(i);
            ArtistEntity artist = new ArtistEntity();
            artist.setId(jsonArtist.getLong(JSONArtistFields.ID));
            artist.setName(jsonArtist.getString(JSONArtistFields.NAME));
            artist.setPhoto(jsonArtist.getString(JSONArtistFields.PHOTO));
            artist.setRating(jsonArtist.getLong(JSONArtistFields.RATING));
            artists.add(artist);
        }
        return artists;
    }

    public static List<GenreEntity> jsonToGenres(JSONArray json) throws JSONException {
        List<GenreEntity> genres = new ArrayList<>(json.length());
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonGenre = json.getJSONObject(i);
            GenreEntity genre = new GenreEntity();
            genre.setId(jsonGenre.getLong(JSONGenreFields.ID));
            genre.setName(jsonGenre.getString(JSONGenreFields.NAME));
            genre.setRating(jsonGenre.getLong(JSONGenreFields.RATING));
            genres.add(genre);
        }
        return genres;
    }

    public static List<TrackEntity> jsonToTracks(JSONArray json) throws JSONException {
        List<TrackEntity> tracks = new ArrayList<>(json.length());
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonTrack = json.getJSONObject(i);
            TrackEntity track = new TrackEntity();
            track.setId(jsonTrack.getLong(JSONTrackFields.ID));
            track.setName(jsonTrack.getString(JSONTrackFields.NAME));
            track.setSong(jsonTrack.getString(JSONTrackFields.SONG));
            track.setCover(jsonTrack.getString(JSONTrackFields.COVER));
            track.setVideo(jsonTrack.getString(JSONTrackFields.VIDEO));
            track.setRating(jsonTrack.getLong(JSONTrackFields.RATING));

            JSONArray artists = jsonTrack.getJSONArray(JSONTrackFields.ARTISTS);
            for (int j = 0; j < artists.length(); j++) {
                JSONObject jsonArtist = artists.getJSONObject(j);
                ArtistEntity artist = new ArtistEntity();
                artist.setId(jsonArtist.getLong(JSONEntityConstants.JSONArtistFields.ID));
                artist.setName(jsonArtist.getString(JSONEntityConstants.JSONArtistFields.NAME));
                track.getArtists().add(artist);
            }

            JSONArray units = jsonTrack.getJSONArray(JSONTrackFields.UNITS);
            for (int j = 0; j < units.length(); j++) {
                JSONObject jsonUnit = units.getJSONObject(j);
                track.getUnits().add(jsonUnit.getString(JSONEntityConstants.JSONUnitFields.NAME));
            }

            tracks.add(track);
        }
        return tracks;
    }

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
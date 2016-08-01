package by.gsu.parser;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import by.gsu.model.Track;

public abstract class AmplitudeJsonParser {

    private static final String SONGS_PARAMETER        = "songs";
    private static final String ALBUM_SONG_KEY         = "album";
    private static final String ARTIST_SONG_KEY        = "artist";
    private static final String COVER_ART_URL_SONG_KEY = "cover_art_url";
    private static final String NAME_SONG_KEY          = "name";
    private static final String URL_SONG_KEY           = "url";

    public static String getAmplitudeJson(final List<Track> tracks) {
        JSONArray jsonArray = new JSONArray();
        for (Track track : tracks) {
            jsonArray.put(getAmplitudeSong(track));
        }
        return new JSONObject().put(SONGS_PARAMETER, jsonArray).toString();
    }

    private static JSONObject getAmplitudeSong(final Track track) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NAME_SONG_KEY, "Get Low");
        jsonObject.put(ARTIST_SONG_KEY, "Lil Jon feat. the East Side Boyz song");
        jsonObject.put(ALBUM_SONG_KEY, "");
        jsonObject.put(URL_SONG_KEY, track.getSong());
        jsonObject.put(COVER_ART_URL_SONG_KEY, track.getCover());
        return jsonObject;
    }

}

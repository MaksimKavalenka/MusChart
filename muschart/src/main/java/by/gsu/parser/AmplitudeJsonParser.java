package by.gsu.parser;

import static by.gsu.constants.UploadConstants.Path.AUDIO_UPLOAD_PATH;
import static by.gsu.constants.UploadConstants.Path.TRACK_COVER_UPLOAD_PATH;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import by.gsu.exception.ValidationException;
import by.gsu.model.Track;

public abstract class AmplitudeJsonParser {

    private static final String SONGS_PARAMETER   = "songs";
    private static final String ALBUM_KEY         = "album";
    private static final String ARTIST_KEY        = "artist";
    private static final String COVER_ART_URL_KEY = "cover_art_url";
    private static final String NAME_KEY          = "name";
    private static final String URL_KEY           = "url";

    public static String getAmplitudeJson(final List<Track> tracks) throws ValidationException {
        JSONArray jsonArray = new JSONArray();
        for (Track track : tracks) {
            jsonArray.put(getAmplitudeSong(track));
        }
        return new JSONObject().put(SONGS_PARAMETER, jsonArray).toString();
    }

    private static JSONObject getAmplitudeSong(final Track track) throws ValidationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NAME_KEY, track.getName());
        jsonObject.put(ARTIST_KEY, ModelJsonParser.getArtistsName(track));
        jsonObject.put(ALBUM_KEY, "");
        jsonObject.put(URL_KEY, AUDIO_UPLOAD_PATH + "/" + track.getSong());
        jsonObject.put(COVER_ART_URL_KEY, TRACK_COVER_UPLOAD_PATH + "/" + track.getCover());
        return jsonObject;
    }

}

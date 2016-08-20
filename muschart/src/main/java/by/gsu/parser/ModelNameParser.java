package by.gsu.parser;

import java.util.LinkedList;
import java.util.List;

import by.gsu.exception.ValidationException;
import by.gsu.model.Artist;
import by.gsu.model.Genre;
import by.gsu.model.Track;

public abstract class ModelNameParser {

    public static List<String> getTrackNames(final List<Track> tracks) throws ValidationException {
        List<String> names = new LinkedList<>();
        for (Track track : tracks) {
            names.add(ModelJsonParser.getArtistsName(track) + " - " + track.getName());
        }
        return names;
    }

    public static List<String> getArtistNames(final List<Artist> artists)
            throws ValidationException {
        List<String> names = new LinkedList<>();
        for (Artist artist : artists) {
            names.add(artist.getName());
        }
        return names;
    }

    public static List<String> getGenreNames(final List<Genre> genres) throws ValidationException {
        List<String> names = new LinkedList<>();
        for (Genre genre : genres) {
            names.add(genre.getName());
        }
        return names;
    }

}

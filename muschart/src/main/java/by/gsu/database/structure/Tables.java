package by.gsu.database.structure;

public enum Tables {

    TRACK, ARTIST, GENRE, USER, TRACK_ARTIST, TRACK_GENRE, ARTIST_GENRE, USER_TRACK, USER_ARTIST, USER_GENRE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}

package by.gsu.database.structure.columns;

public enum TrackColumns {

    ID, NAME, SONG, COVER, RATING;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}

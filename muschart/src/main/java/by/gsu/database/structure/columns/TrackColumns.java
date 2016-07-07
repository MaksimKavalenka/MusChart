package by.gsu.database.structure.columns;

public enum TrackColumns {

    ID, NAME, SONG, COVER, DATE, RATING;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}

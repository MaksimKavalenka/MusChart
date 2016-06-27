package by.gsu.database.structure.columns;

public enum ArtistColumns {

    ID, NAME, PHOTO, RATING;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}

package by.gsu.database.structure.columns;

public enum RelationColumns {

    ID_TRACK, ID_ARTIST, ID_GENRE, ID_USER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}

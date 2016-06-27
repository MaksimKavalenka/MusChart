package by.gsu.database.structure.columns;

public enum GenreColumns {

    ID, NAME, RATING;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}

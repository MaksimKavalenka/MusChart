package by.gsu.database.structure.columns;

public enum UserColumns {

    ID, LOGIN, PASSWORD, ROLE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}

package by.gsu.model;

public enum Role {

    USER, ADMIN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}

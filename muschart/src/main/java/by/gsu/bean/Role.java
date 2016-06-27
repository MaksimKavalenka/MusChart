package by.gsu.bean;

public enum Role {

    USER, ADMIN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}

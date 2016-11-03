package com.muschart.constants;

public enum RoleConstants {

    ROLE_USER("USER"), ROLE_ADMIN("ADMIN");

    private String role;

    private RoleConstants(final String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }

}

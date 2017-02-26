package com.muschart.dto.input;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class GenreInputDTO implements Serializable {

    private static final long serialVersionUID = 832094874784483065L;

    @NotNull
    private String            name;

    public GenreInputDTO() {
    }

    public GenreInputDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name:" + name + "]";
    }

}
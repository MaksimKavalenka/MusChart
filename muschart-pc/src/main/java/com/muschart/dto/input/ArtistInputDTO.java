package com.muschart.dto.input;

import java.io.Serializable;
import java.util.ArrayList;

import javax.validation.constraints.NotNull;

public class ArtistInputDTO implements Serializable {

    private static final long serialVersionUID = 7743108250263166783L;

    @NotNull
    private String            name;

    @NotNull
    private String            photo;

    @NotNull
    private ArrayList<Long>   genresId;

    public ArtistInputDTO() {
    }

    public ArtistInputDTO(String name, String photo, ArrayList<Long> genresId) {
        this.name = name;
        this.photo = photo;
        this.genresId = genresId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<Long> getGenresId() {
        return genresId;
    }

    public void setGenresId(ArrayList<Long> genresId) {
        this.genresId = genresId;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name:" + name + ",photo:" + photo + "]";
    }

}
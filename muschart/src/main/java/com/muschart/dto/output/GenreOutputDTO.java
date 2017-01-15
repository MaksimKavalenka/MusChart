package com.muschart.dto.output;

import com.muschart.dto.AbstractDTO;
import com.muschart.entity.GenreEntity;

public class GenreOutputDTO extends AbstractDTO {

    private static final long serialVersionUID = -8290252196532407134L;

    private String            name;
    private long              rating;
    private boolean           isLiked;

    public GenreOutputDTO() {
        super();
    }

    public GenreOutputDTO(long id, String name, long rating, boolean isLiked) {
        super(id);
        this.name = name;
        this.rating = rating;
        this.isLiked = isLiked;
    }

    public GenreOutputDTO(GenreEntity genre, boolean isLiked) {
        super(genre.getId());
        name = genre.getName();
        rating = genre.getRating();
        this.isLiked = isLiked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + ",rating:" + rating + ",liked:" + isLiked + "]";
    }

}
package com.muschart.dto;

import com.muschart.entity.GenreEntity;

public class GenreDTO extends GenreEntity {

    private static final long serialVersionUID = -8290252196532407134L;

    private boolean           isLiked;

    public GenreDTO() {
        super();
    }

    public GenreDTO(long id, String name, long rating, boolean isLiked) {
        super(id, name, rating, null, null, null);
        this.isLiked = isLiked;
    }

    public GenreDTO(GenreEntity genre, boolean isLiked) {
        super(genre.getId(), genre.getName(), genre.getRating(), null, null, null);
        this.isLiked = isLiked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + super.getName()
                + ",rating:" + super.getRating() + ",liked:" + isLiked + "]";
    }

}

package com.muschart.dto;

import com.muschart.entity.ArtistEntity;

public class ArtistDTO extends ArtistEntity {

    private static final long serialVersionUID = 8626742763677694341L;

    private boolean           isLiked;

    public ArtistDTO() {
        super();
    }

    public ArtistDTO(long id, String name, String photo, long rating, boolean isLiked) {
        super(id, name, photo, rating, null, null, null);
        this.isLiked = isLiked;
    }

    public ArtistDTO(ArtistEntity artist, boolean isLiked) {
        super(artist.getId(), artist.getName(), artist.getPhoto(), artist.getRating(), null, null,
                null);
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
                + ",photo:" + super.getPhoto() + ",rating:" + super.getRating() + ",liked:"
                + isLiked + "]";
    }

}
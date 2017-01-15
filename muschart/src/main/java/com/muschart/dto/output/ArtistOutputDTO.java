package com.muschart.dto.output;

import com.muschart.dto.AbstractDTO;
import com.muschart.entity.ArtistEntity;

public class ArtistOutputDTO extends AbstractDTO {

    private static final long serialVersionUID = 8626742763677694341L;

    private String            name;
    private String            photo;
    private long              rating;
    private boolean           isLiked;

    public ArtistOutputDTO() {
        super();
    }

    public ArtistOutputDTO(long id, String name, String photo, long rating, boolean isLiked) {
        super(id);
        this.name = name;
        this.photo = photo;
        this.rating = rating;
        this.isLiked = isLiked;
    }

    public ArtistOutputDTO(ArtistEntity artist, boolean isLiked) {
        super(artist.getId());
        name = artist.getName();
        photo = artist.getPhoto();
        rating = artist.getRating();
        this.isLiked = isLiked;
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
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + ",photo:" + photo + ",rating:" + rating + ",liked:" + isLiked + "]";
    }

}
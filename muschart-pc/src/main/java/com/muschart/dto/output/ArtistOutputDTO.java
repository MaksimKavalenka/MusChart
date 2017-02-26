package com.muschart.dto.output;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.muschart.constants.DefaultConstants;
import com.muschart.dto.AbstractDTO;
import com.muschart.entity.ArtistEntity;

public class ArtistOutputDTO extends AbstractDTO {

    private static final long serialVersionUID = 8626742763677694341L;

    @NotNull
    private String            name;

    @NotNull
    private String            photo;

    @NotNull
    @Min(value = DefaultConstants.MIN_RATING)
    private Long              rating;

    @NotNull
    private Boolean           isLiked;

    public ArtistOutputDTO() {
        super();
    }

    public ArtistOutputDTO(long id, String name, String photo, Long rating, Boolean isLiked) {
        super(id);
        this.name = name;
        this.photo = photo;
        this.rating = rating;
        this.isLiked = isLiked;
    }

    public ArtistOutputDTO(ArtistEntity artist, Boolean isLiked) {
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

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Boolean isLiked() {
        return isLiked;
    }

    public void setLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + ",photo:" + photo + ",rating:" + rating + ",liked:" + isLiked + "]";
    }

}
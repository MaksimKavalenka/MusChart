package com.muschart.dto.output;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.muschart.constants.DefaultConstants;
import com.muschart.dto.AbstractDTO;
import com.muschart.entity.GenreEntity;

public class GenreOutputDTO extends AbstractDTO {

    private static final long serialVersionUID = -8290252196532407134L;

    @NotNull
    private String            name;

    @NotNull
    @Min(value = DefaultConstants.MIN_RATING)
    private Long              rating;

    @NotNull
    private Boolean           isLiked;

    public GenreOutputDTO() {
        super();
    }

    public GenreOutputDTO(long id, String name, Long rating, boolean isLiked) {
        super(id);
        this.name = name;
        this.rating = rating;
        this.isLiked = isLiked;
    }

    public GenreOutputDTO(GenreEntity genre, Boolean isLiked) {
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
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + ",rating:" + rating + ",liked:" + isLiked + "]";
    }

}
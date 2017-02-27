package com.muschart.entity;

public class ArtistEntity extends AbstractEntity {

    private String name;
    private String photo;
    private long rating;

    public ArtistEntity() {
        super();
    }

    public ArtistEntity(String name, String photo, long rating) {
        super();
        this.name = name;
        this.photo = photo;
        this.rating = rating;
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

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + ",photo:" + photo + ",rating:" + rating + "]";
    }

}
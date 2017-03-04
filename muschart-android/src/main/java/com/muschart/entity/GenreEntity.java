package com.muschart.entity;

public class GenreEntity extends AbstractEntity {

    private String name;
    private long rating;

    public GenreEntity() {
        super();
    }

    public GenreEntity(String name, long rating) {
        super();
        this.name = name;
        this.rating = rating;
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

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + ",rating:" + rating + "]";
    }

}
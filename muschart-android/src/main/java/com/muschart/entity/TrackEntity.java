package com.muschart.entity;

import com.muschart.constants.UrlConstants;

import java.util.ArrayList;
import java.util.List;

public class TrackEntity extends AbstractEntity {

    private String name;
    private String song;
    private String cover;
    private String video;
    private long rating;
    private List<ArtistEntity> artists;
    private List<String> units;

    public TrackEntity() {
        super();
        artists = new ArrayList<>(0);
        units = new ArrayList<>(0);
    }

    public TrackEntity(String name, String song, String cover, String video, long rating, List<ArtistEntity> artists, List<String> units) {
        super();
        this.name = name;
        this.song = song;
        this.cover = cover;
        this.video = video;
        this.rating = rating;
        this.artists = artists;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public List<ArtistEntity> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistEntity> artists) {
        this.artists = artists;
    }

    public List<String> getUnits() {
        return units;
    }

    public void setUnitsOrder(List<String> units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + ",song:" + song + ",cover:" + cover + ",video:" + video + ",rating:" + rating + "]";
    }

}
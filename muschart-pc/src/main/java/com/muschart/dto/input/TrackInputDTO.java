package com.muschart.dto.input;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class TrackInputDTO implements Serializable {

    private static final long serialVersionUID = -6214561432107289969L;

    @NotNull
    private String            name;

    @NotNull
    private String            song;

    @NotNull
    private String            cover;

    @NotNull
    private Date              release;

    @NotNull
    private List<Long>        artists;

    @NotNull
    private List<Long>        genres;

    private String            video;
    private List<Long>        units;

    public TrackInputDTO() {
    }

    public TrackInputDTO(String name, String song, String cover, String video, Date release, List<Long> artists, List<Long> units, List<Long> genres) {
        this.name = name;
        this.song = song;
        this.cover = cover;
        this.video = video;
        this.release = release;
        this.artists = artists;
        this.units = units;
        this.genres = genres;
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

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public List<Long> getArtists() {
        return artists;
    }

    public void setArtists(List<Long> artists) {
        this.artists = artists;
    }

    public List<Long> getUnits() {
        return units;
    }

    public void setUnits(List<Long> units) {
        this.units = units;
    }

    public List<Long> getGenres() {
        return genres;
    }

    public void setGenres(List<Long> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name:" + name + ",song:" + song + ",cover:" + cover + ",video:" + video + ",release:" + release + "]";
    }

}
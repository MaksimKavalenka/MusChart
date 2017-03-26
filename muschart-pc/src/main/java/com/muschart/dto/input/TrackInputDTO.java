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
    private List<Long>        artistsId;

    @NotNull
    private List<Long>        genresId;

    private String            video;
    private List<Long>        unitsId;

    public TrackInputDTO() {
    }

    public TrackInputDTO(String name, String song, String cover, String video, Date release, List<Long> artistsId, List<Long> unitsId, List<Long> genresId) {
        this.name = name;
        this.song = song;
        this.cover = cover;
        this.video = video;
        this.release = release;
        this.artistsId = artistsId;
        this.unitsId = unitsId;
        this.genresId = genresId;
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

    public List<Long> getArtistsId() {
        return artistsId;
    }

    public void setArtistsId(List<Long> artistsId) {
        this.artistsId = artistsId;
    }

    public List<Long> getUnitsId() {
        return unitsId;
    }

    public void setUnitsId(List<Long> unitsId) {
        this.unitsId = unitsId;
    }

    public List<Long> getGenresId() {
        return genresId;
    }

    public void setGenresId(List<Long> genresId) {
        this.genresId = genresId;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name:" + name + ",song:" + song + ",cover:" + cover + ",video:" + video + ",release:" + release + "]";
    }

}
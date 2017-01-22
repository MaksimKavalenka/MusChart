package com.muschart.dto.output;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.muschart.constants.DefaultConstants;
import com.muschart.dto.AbstractDTO;
import com.muschart.dto.IdAndNameDTO;
import com.muschart.entity.TrackEntity;

public class TrackOutputDTO extends AbstractDTO {

    private static final long  serialVersionUID = 4257174055910464860L;

    @NotNull
    private String             name;

    @NotNull
    private String             song;

    @NotNull
    private String             cover;

    @NotNull
    private Date               release;

    @NotNull
    @Min(value = DefaultConstants.MIN_RATING)
    private Long               rating;

    @NotNull
    private Boolean            isLiked;

    @NotNull
    private List<IdAndNameDTO> artists;

    private String             video;
    private List<IdAndNameDTO> units;

    public TrackOutputDTO() {
        super();
    }

    public TrackOutputDTO(long id, String name, String song, String cover, String video, Date release, Long rating, Boolean isLiked, List<IdAndNameDTO> artists, List<IdAndNameDTO> units) {
        super(id);
        this.name = name;
        this.song = song;
        this.cover = cover;
        this.video = video;
        this.release = release;
        this.rating = rating;
        this.isLiked = isLiked;
        this.artists = artists;
        this.units = units;
    }

    public TrackOutputDTO(TrackEntity track, Boolean isLiked, List<IdAndNameDTO> artists, List<IdAndNameDTO> units) {
        super(track.getId());
        name = track.getName();
        song = track.getSong();
        cover = track.getCover();
        video = track.getVideo();
        release = track.getRelease();
        rating = track.getRating();
        this.isLiked = isLiked;
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

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
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

    public List<IdAndNameDTO> getArtists() {
        return artists;
    }

    public void setArtists(List<IdAndNameDTO> artists) {
        this.artists = artists;
    }

    public List<IdAndNameDTO> getUnits() {
        return units;
    }

    public void setUnits(List<IdAndNameDTO> units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + ",song:" + song + ",cover:" + cover + ",video:" + video + ",release:" + release + ",rating:" + rating + ",liked:" + isLiked + "]";
    }

}
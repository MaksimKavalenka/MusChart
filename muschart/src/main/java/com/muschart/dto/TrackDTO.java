package com.muschart.dto;

import java.util.Date;
import java.util.List;

import com.muschart.entity.TrackEntity;

public class TrackDTO extends TrackEntity {

    private static final long  serialVersionUID = 4257174055910464860L;

    private boolean            isLiked;
    private List<IdAndNameDTO> artists;
    private List<IdAndNameDTO> units;

    public TrackDTO() {
        super();
    }

    public TrackDTO(long id, String name, String song, String cover, String video, Date release,
            long rating, boolean isLiked, List<IdAndNameDTO> artists, List<IdAndNameDTO> units) {
        super(id, name, song, cover, video, release, rating, null, null, null, null);
        this.isLiked = isLiked;
        this.artists = artists;
        this.units = units;
    }

    public TrackDTO(TrackEntity track, boolean isLiked, List<IdAndNameDTO> artists,
            List<IdAndNameDTO> units) {
        super(track.getId(), track.getName(), track.getSong(), track.getCover(), track.getVideo(),
                track.getRelease(), track.getRating(), null, null, null, null);
        this.isLiked = isLiked;
        this.artists = artists;
        this.units = units;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean isLiked) {
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
        return getClass().getName() + "[id:" + super.getId() + ",name:" + super.getName() + ",song:"
                + super.getSong() + ",cover:" + super.getCover() + ",video:" + super.getVideo()
                + ",release:" + super.getRelease() + ",rating:" + super.getRating() + ",liked:"
                + isLiked + "]";
    }

}

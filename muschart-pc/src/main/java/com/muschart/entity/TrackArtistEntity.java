package com.muschart.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "track_artist")
public class TrackArtistEntity extends AbstractEntity {

    private static final long serialVersionUID = -5575941823820373570L;

    @ManyToOne(targetEntity = TrackEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private TrackEntity       track;

    @ManyToOne(targetEntity = ArtistEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private ArtistEntity      artist;

    public TrackArtistEntity() {
        super();
    }

    public TrackArtistEntity(TrackEntity track, ArtistEntity artist) {
        super();
        this.track = track;
        this.artist = artist;
    }

    public TrackArtistEntity(long id, TrackEntity track, ArtistEntity artist) {
        super(id);
        this.track = track;
        this.artist = artist;
    }

    public TrackEntity getTrack() {
        return track;
    }

    public void setTrack(TrackEntity track) {
        this.track = track;
    }

    public ArtistEntity getArtist() {
        return artist;
    }

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",trackId:" + track.getId() + ",artistId:" + artist.getId() + "]";
    }

}
package com.muschart.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "genre")
public class GenreEntity extends AbstractEntity {

    private static final long  serialVersionUID = 2354473633918150319L;

    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String             name;

    @Column(name = "rating", nullable = false, columnDefinition = "long default 0")
    private long               rating;

    @JsonIgnore
    @ManyToMany(targetEntity = TrackEntity.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "track_genre", joinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false))
    private List<TrackEntity>  tracks;

    @JsonIgnore
    @ManyToMany(targetEntity = ArtistEntity.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "artist_genre", joinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false))
    private List<ArtistEntity> artists;

    @JsonIgnore
    @ManyToMany(targetEntity = UserEntity.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "user_genre", joinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false))
    private List<UserEntity>   users;

    public GenreEntity() {
        super();
    }

    public GenreEntity(final String name) {
        super();
        this.name = name;
    }

    public GenreEntity(final String name, final long rating, final List<TrackEntity> tracks,
            final List<ArtistEntity> artists, final List<UserEntity> users) {
        super();
        this.name = name;
        this.rating = rating;
        this.tracks = tracks;
        this.artists = artists;
        this.users = users;
    }

    public GenreEntity(final long id, final String name, final long rating,
            final List<TrackEntity> tracks, final List<ArtistEntity> artists,
            final List<UserEntity> users) {
        super(id);
        this.name = name;
        this.rating = rating;
        this.tracks = tracks;
        this.artists = artists;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(final long rating) {
        this.rating = rating;
    }

    public List<TrackEntity> getTracks() {
        return tracks;
    }

    public void setTracks(final List<TrackEntity> tracks) {
        this.tracks = tracks;
    }

    public List<ArtistEntity> getArtists() {
        return artists;
    }

    public void setArtists(final List<ArtistEntity> artists) {
        this.artists = artists;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(final List<UserEntity> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + ",rating:" + rating
                + "]";
    }

}

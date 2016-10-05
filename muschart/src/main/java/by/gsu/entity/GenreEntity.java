package by.gsu.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "genre")
public class GenreEntity extends AbstractEntity {

    private static final long serialVersionUID = 2354473633918150319L;

    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String            name;

    @Column(name = "rating", nullable = false)
    @DefaultValue(value = "0")
    private long              rating;

    @JsonIgnore
    @ManyToMany(targetEntity = TrackEntity.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "track_genre", joinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false))
    private Set<TrackEntity>  tracks;

    @JsonIgnore
    @ManyToMany(targetEntity = ArtistEntity.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "artist_genre", joinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false))
    private Set<ArtistEntity> artists;

    @JsonIgnore
    @ManyToMany(targetEntity = UserEntity.class, cascade = {CascadeType.DETACH})
    @JoinTable(name = "user_genre", joinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false))
    private Set<UserEntity>   users;

    public GenreEntity() {
        super();
    }

    public GenreEntity(final String name) {
        super();
        this.name = name;
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

    public Set<TrackEntity> getTracks() {
        return tracks;
    }

    public void setTracks(final Set<TrackEntity> tracks) {
        this.tracks = tracks;
    }

    public Set<ArtistEntity> getArtists() {
        return artists;
    }

    public void setArtists(final Set<ArtistEntity> artists) {
        this.artists = artists;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(final Set<UserEntity> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Genre [id=" + super.getId() + ", name=" + name + ", rating=" + rating + "]";
    }

}

package by.gsu.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;

@Entity
@Table(name = "artist")
public class Artist extends Model {

    private static final long serialVersionUID = 4082508064669884035L;

    @Column(name = "name", nullable = false, length = 255)
    private String            name;

    @Column(name = "photo", nullable = false, length = 255)
    private String            photo;

    @Column(name = "rating", nullable = false, length = 5)
    @DefaultValue(value = "0")
    private int               rating;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, targetEntity = Track.class)
    @JoinTable(name = "track_artist", joinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false))
    private List<Track>       tracks;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, targetEntity = Genre.class)
    @JoinTable(name = "artist_genre", joinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false))
    private List<Genre>       genres;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, targetEntity = User.class)
    @JoinTable(name = "user_artist", joinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false))
    private List<User>        users;

    public Artist() {
        super();
    }

    public Artist(final int id, final String name, final String photo, final int rating) {
        super(id);
        this.name = name;
        this.photo = photo;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(final List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(final List<Genre> genres) {
        this.genres = genres;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

}

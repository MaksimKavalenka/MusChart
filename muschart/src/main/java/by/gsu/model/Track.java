package by.gsu.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.ws.rs.DefaultValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "track")
public class Track extends Model {

    private static final long serialVersionUID = 1952582684617860747L;

    @Column(name = "artist_name", nullable = false, length = 255)
    private String            artistName;

    @Column(name = "song_name", nullable = false, length = 255)
    private String            songName;

    @Column(name = "song", nullable = false, columnDefinition = "TEXT")
    private String            song;

    @Column(name = "cover", nullable = false, columnDefinition = "TEXT")
    private String            cover;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date              date;

    @Column(name = "rating", nullable = false)
    @DefaultValue(value = "0")
    private long              rating;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, targetEntity = Artist.class)
    @JoinTable(name = "track_artist", joinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false))
    @JsonIgnore
    private List<Artist>      artists;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, targetEntity = Genre.class)
    @JoinTable(name = "track_genre", joinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false))
    @JsonIgnore
    private List<Genre>       genres;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, targetEntity = User.class)
    @JoinTable(name = "user_track", joinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false))
    @JsonIgnore
    private List<User>        users;

    public Track() {
        super();
    }

    public Track(final long id, final String artistName, final String songName, final String song,
            final String cover, final Date date, final long rating) {
        super(id);
        this.artistName = artistName;
        this.songName = songName;
        this.song = song;
        this.cover = cover;
        this.date = date;
        this.rating = rating;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(final String artistName) {
        this.artistName = artistName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(final String songName) {
        this.songName = songName;
    }

    public String getSong() {
        return song;
    }

    public void setSong(final String song) {
        this.song = song;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(final String cover) {
        this.cover = cover;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(final long rating) {
        this.rating = rating;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(final List<Artist> artists) {
        this.artists = artists;
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

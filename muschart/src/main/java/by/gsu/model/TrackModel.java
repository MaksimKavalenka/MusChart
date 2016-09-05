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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "track")
public class TrackModel extends Model {

    private static final long serialVersionUID = 1952582684617860747L;

    @Column(name = "name", nullable = false, length = 255)
    private String            name;

    @Column(name = "song", nullable = false, length = 255)
    private String            song;

    @Column(name = "cover", nullable = false, length = 255)
    private String            cover;

    @Column(name = "video", length = 255)
    private String            video;

    @Column(name = "released", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date              release;

    @Column(name = "rating", nullable = false)
    @DefaultValue(value = "0")
    private long              rating;

    @ManyToMany(targetEntity = UnitModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "track_unit", joinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_unit", nullable = false, updatable = false))
    private List<UnitModel>   units;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = ArtistModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "track_artist", joinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false))
    private List<ArtistModel> artists;

    @JsonIgnore
    @ManyToMany(targetEntity = GenreModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "track_genre", joinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false))
    private List<GenreModel>  genres;

    @JsonIgnore
    @ManyToMany(targetEntity = UserModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "user_track", joinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false))
    private List<UserModel>   users;

    public TrackModel() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(final String video) {
        this.video = video;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(final Date release) {
        this.release = release;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(final long rating) {
        this.rating = rating;
    }

    public List<UnitModel> getUnits() {
        return units;
    }

    public void setUnits(final List<UnitModel> units) {
        this.units = units;
    }

    public List<ArtistModel> getArtists() {
        return artists;
    }

    public void setArtists(final List<ArtistModel> artists) {
        this.artists = artists;
    }

    public List<GenreModel> getGenres() {
        return genres;
    }

    public void setGenres(final List<GenreModel> genres) {
        this.genres = genres;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(final List<UserModel> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Track [id=" + super.getId() + ", name=" + name + ", song=" + song + ", cover="
                + cover + ", video=" + video + ", release=" + release + ", rating=" + rating + "]";
    }

}

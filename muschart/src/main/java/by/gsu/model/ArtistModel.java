package by.gsu.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "artist")
public class ArtistModel extends Model {

    private static final long serialVersionUID = 4082508064669884035L;

    @Column(name = "name", nullable = false, length = 255)
    private String            name;

    @Column(name = "photo", nullable = false, length = 255)
    private String            photo;

    @Column(name = "rating", nullable = false)
    @DefaultValue(value = "0")
    private long              rating;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = TrackModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "track_artist", joinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false))
    private List<TrackModel>  tracks;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = GenreModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "artist_genre", joinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false))
    private List<GenreModel>  genres;

    @JsonIgnore
    @ManyToMany(targetEntity = UserModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "user_artist", joinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false))
    private List<UserModel>   users;

    public ArtistModel() {
        super();
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

    public long getRating() {
        return rating;
    }

    public void setRating(final long rating) {
        this.rating = rating;
    }

    public List<TrackModel> getTracks() {
        return tracks;
    }

    public void setTracks(final List<TrackModel> tracks) {
        this.tracks = tracks;
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
        return "Artist [id=" + super.getId() + ", name=" + name + ", photo=" + photo + ", rating="
                + rating + "]";
    }

}

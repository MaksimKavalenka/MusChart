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
@Table(name = "genre")
public class GenreModel extends Model {

    private static final long serialVersionUID = 2354473633918150319L;

    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String            name;

    @Column(name = "rating", nullable = false)
    @DefaultValue(value = "0")
    private long              rating;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = TrackModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "track_genre", joinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false))
    private List<TrackModel>  tracks;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = ArtistModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "artist_genre", joinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_artist", nullable = false, updatable = false))
    private List<ArtistModel> artists;

    @JsonIgnore
    @ManyToMany(targetEntity = UserModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "user_genre", joinColumns = @JoinColumn(name = "id_genre", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false))
    private List<UserModel>   users;

    public GenreModel() {
        super();
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

    public List<TrackModel> getTracks() {
        return tracks;
    }

    public void setTracks(final List<TrackModel> tracks) {
        this.tracks = tracks;
    }

    public List<ArtistModel> getArtists() {
        return artists;
    }

    public void setArtists(final List<ArtistModel> artists) {
        this.artists = artists;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(final List<UserModel> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Genre [id=" + super.getId() + ", name=" + name + ", rating=" + rating + "]";
    }

}

package by.gsu.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import by.gsu.entity.TrackEntity;

public class TrackInfo implements Serializable {

    private static final long     serialVersionUID = 4478545688994580431L;

    private long                  id;
    private String                name;
    private String                song;
    private String                cover;
    private String                video;
    private Date                  release;
    private long                  rating;
    private List<IdAndNameEntity> artists;
    private List<IdAndNameEntity> units;

    public TrackInfo() {
    }

    public TrackInfo(final TrackEntity track, final List<IdAndNameEntity> artists,
            final List<IdAndNameEntity> units) {
        id = track.getId();
        name = track.getName();
        song = track.getSong();
        cover = track.getCover();
        video = track.getVideo();
        release = track.getRelease();
        rating = track.getRating();
        this.artists = artists;
        this.units = units;
    }

    public TrackInfo(final long id, final String name, final String song, final String cover,
            final String video, final Date release, final long rating,
            final List<IdAndNameEntity> artists, final List<IdAndNameEntity> units) {
        this.id = id;
        this.name = name;
        this.song = song;
        this.cover = cover;
        this.video = video;
        this.release = release;
        this.rating = rating;
        this.artists = artists;
        this.units = units;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
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

    public List<IdAndNameEntity> getArtists() {
        return artists;
    }

    public void setArtists(final List<IdAndNameEntity> artists) {
        this.artists = artists;
    }

    public List<IdAndNameEntity> getUnits() {
        return units;
    }

    public void setUnits(final List<IdAndNameEntity> units) {
        this.units = units;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TrackInfo other = (TrackInfo) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TrackInfo [id=" + id + ", name=" + name + ", song=" + song + ", cover=" + cover
                + ", video=" + video + ", release=" + release + ", rating=" + rating + "]";
    }

}

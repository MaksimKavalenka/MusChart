package by.gsu.bean.entity;

import java.util.Date;
import java.util.List;

import by.gsu.entity.TrackEntity;

public class TrackInfoEntity extends TrackEntity {

    private static final long     serialVersionUID = 4478545688994580431L;

    private boolean               isLiked;
    private List<IdAndNameEntity> artists;
    private List<IdAndNameEntity> units;

    public TrackInfoEntity() {
        super();
    }

    public TrackInfoEntity(final long id, final String name, final String song, final String cover,
            final String video, final Date release, final long rating, final boolean isLiked,
            final List<IdAndNameEntity> artists, final List<IdAndNameEntity> units) {
        super(id, name, song, cover, video, release, rating, null, null, null, null);
        this.isLiked = isLiked;
        this.artists = artists;
        this.units = units;
    }

    public TrackInfoEntity(final TrackEntity track, final boolean isLiked,
            final List<IdAndNameEntity> artists, final List<IdAndNameEntity> units) {
        super(track.getId(), track.getName(), track.getSong(), track.getCover(), track.getVideo(),
                track.getRelease(), track.getRating(), null, null, null, null);
        this.isLiked = isLiked;
        this.artists = artists;
        this.units = units;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(final boolean isLiked) {
        this.isLiked = isLiked;
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
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + super.getName() + ",song:"
                + super.getSong() + ",cover:" + super.getCover() + ",video:" + super.getVideo()
                + ",release:" + super.getRelease() + ",rating:" + super.getRating() + ",isLiked:"
                + isLiked + "]";
    }

}

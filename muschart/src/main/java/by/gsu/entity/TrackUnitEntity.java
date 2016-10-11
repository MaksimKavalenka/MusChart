package by.gsu.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "track_unit")
public class TrackUnitEntity extends AbstractEntity {

    private static final long serialVersionUID = 2344490782217598796L;

    @ManyToOne(targetEntity = TrackEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private TrackEntity       track;

    @ManyToOne(targetEntity = UnitEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private UnitEntity        unit;

    public TrackUnitEntity() {
        super();
    }

    public TrackUnitEntity(final TrackEntity track, final UnitEntity unit) {
        super();
        this.track = track;
        this.unit = unit;
    }

    public TrackUnitEntity(final long id, final TrackEntity track, final UnitEntity unit) {
        super(id);
        this.track = track;
        this.unit = unit;
    }

    public TrackEntity getTrack() {
        return track;
    }

    public void setTrack(final TrackEntity track) {
        this.track = track;
    }

    public UnitEntity getUnit() {
        return unit;
    }

    public void setUnit(final UnitEntity unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "TrackUnit [id=" + super.getId() + "trackId=" + track.getId() + ", unitId="
                + unit.getId() + "]";
    }

}

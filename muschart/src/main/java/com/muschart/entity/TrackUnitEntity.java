package com.muschart.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "track_unit")
public class TrackUnitEntity extends AbstractEntity {

    private static final long serialVersionUID = -5953132758596954890L;

    @ManyToOne(targetEntity = TrackEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private TrackEntity       track;

    @ManyToOne(targetEntity = UnitEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private UnitEntity        unit;

    public TrackUnitEntity() {
        super();
    }

    public TrackUnitEntity(TrackEntity track, UnitEntity unit) {
        super();
        this.track = track;
        this.unit = unit;
    }

    public TrackUnitEntity(long id, TrackEntity track, UnitEntity unit) {
        super(id);
        this.track = track;
        this.unit = unit;
    }

    public TrackEntity getTrack() {
        return track;
    }

    public void setTrack(TrackEntity track) {
        this.track = track;
    }

    public UnitEntity getUnit() {
        return unit;
    }

    public void setUnit(UnitEntity unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",trackId:" + track.getId()
                + ",unitId:" + unit.getId() + "]";
    }

}

package com.muschart.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "unit")
public class UnitEntity extends AbstractEntity {

    private static final long     serialVersionUID = -568108371094521785L;

    @Column(name = "name", nullable = false, length = 255)
    private String                name;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.DETACH}, mappedBy = "track")
    private List<TrackUnitEntity> tracksOrder;

    public UnitEntity() {
        super();
    }

    public UnitEntity(String name) {
        super();
        this.name = name;
    }

    public UnitEntity(String name, List<TrackUnitEntity> tracksOrder) {
        super();
        this.name = name;
        this.tracksOrder = tracksOrder;
    }

    public UnitEntity(long id, String name, List<TrackUnitEntity> tracksOrder) {
        super(id);
        this.name = name;
        this.tracksOrder = tracksOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TrackUnitEntity> getTracksOrder() {
        return tracksOrder;
    }

    public void setTracksOrder(List<TrackUnitEntity> tracksOrder) {
        this.tracksOrder = tracksOrder;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + "]";
    }

}
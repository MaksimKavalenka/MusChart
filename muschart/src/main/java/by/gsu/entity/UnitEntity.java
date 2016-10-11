package by.gsu.entity;

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

    private static final long     serialVersionUID = -6324472595495881439L;

    @Column(name = "name", nullable = false, length = 255)
    private String                name;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.DETACH}, mappedBy = "track")
    private List<TrackUnitEntity> tracksOrder;

    public UnitEntity() {
        super();
    }

    public UnitEntity(final String name) {
        super();
        this.name = name;
    }

    public UnitEntity(final String name, final List<TrackUnitEntity> tracksOrder) {
        super();
        this.name = name;
        this.tracksOrder = tracksOrder;
    }

    public UnitEntity(final long id, final String name, final List<TrackUnitEntity> tracksOrder) {
        super(id);
        this.name = name;
        this.tracksOrder = tracksOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<TrackUnitEntity> getTracksOrder() {
        return tracksOrder;
    }

    public void setTracksOrder(final List<TrackUnitEntity> tracksOrder) {
        this.tracksOrder = tracksOrder;
    }

    @Override
    public String toString() {
        return "Unit [id=" + super.getId() + ", name=" + name + "]";
    }

}

package by.gsu.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "unit")
public class UnitModel extends Model {

    private static final long serialVersionUID = -6324472595495881439L;

    @Column(name = "name", nullable = false, length = 255)
    private String            name;

    @JsonIgnore
    @ManyToMany(targetEntity = TrackModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "track_unit", joinColumns = @JoinColumn(name = "id_unit", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_track", nullable = false, updatable = false))
    private List<TrackModel>  tracks;

    public UnitModel() {
        super();
    }

    public UnitModel(final String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<TrackModel> getTracks() {
        return tracks;
    }

    public void setTracks(final List<TrackModel> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "Unit [id=" + super.getId() + ", name=" + name + "]";
    }

}

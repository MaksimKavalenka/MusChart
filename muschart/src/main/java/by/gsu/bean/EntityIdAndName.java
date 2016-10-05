package by.gsu.bean;

import java.io.Serializable;

public class EntityIdAndName implements Serializable {

    private static final long serialVersionUID = 7794693790810942212L;

    private long              id;
    private String            name;

    public EntityIdAndName() {
    }

    public EntityIdAndName(final long id, final String name) {
        this.id = id;
        this.name = name;
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
        EntityIdAndName other = (EntityIdAndName) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityBody [id=" + id + ", name=" + name + "]";
    }

}

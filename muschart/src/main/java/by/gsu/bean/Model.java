package by.gsu.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Model implements Comparable<Model>, Serializable {

    private static final long serialVersionUID = 702208025730502671L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, length = 5)
    private int               id;

    public Model() {
    }

    public Model(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        Model other = (Model) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(final Model o) {
        return id - o.getId();
    }

}

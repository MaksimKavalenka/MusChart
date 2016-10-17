package by.gsu.bean.entity;

import by.gsu.entity.AbstractEntity;

public class IdAndNameEntity extends AbstractEntity {

    private static final long serialVersionUID = 7794693790810942212L;

    private String            name;

    public IdAndNameEntity() {
        super();
    }

    public IdAndNameEntity(final long id, final String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + "]";
    }

}

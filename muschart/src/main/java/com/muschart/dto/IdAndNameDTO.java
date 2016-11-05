package com.muschart.dto;

import com.muschart.entity.AbstractEntity;

public class IdAndNameDTO extends AbstractEntity {

    private static final long serialVersionUID = 7794693790810942212L;

    private String            name;

    public IdAndNameDTO() {
        super();
    }

    public IdAndNameDTO(final long id, final String name) {
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
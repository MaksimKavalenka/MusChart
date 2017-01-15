package com.muschart.dto;

import java.io.Serializable;

public class EntityDTO implements Serializable {

    private static final long serialVersionUID = 3703656558746480100L;

    private String            entity;
    private long              entityId;

    public EntityDTO() {
    }

    public EntityDTO(String entity, long entityId) {
        this.entity = entity;
        this.entityId = entityId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[entity:" + entity + ",entityId:" + entityId + "]";
    }

}
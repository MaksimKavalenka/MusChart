package com.muschart.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.muschart.constants.DefaultConstants;

public class EntityDTO implements Serializable {

    private static final long serialVersionUID = 3703656558746480100L;

    @NotNull
    private String            entity;

    @NotNull
    @Min(value = DefaultConstants.MIN_ID)
    private Long              entityId;

    public EntityDTO() {
    }

    public EntityDTO(String entity, Long entityId) {
        this.entity = entity;
        this.entityId = entityId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[entity:" + entity + ",entityId:" + entityId + "]";
    }

}
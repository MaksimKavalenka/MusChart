package com.muschart.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.muschart.constants.DefaultConstants;

public abstract class AbstractDTO implements Serializable {

    private static final long serialVersionUID = -3248976059943166282L;

    @NotNull
    @Min(value = DefaultConstants.MIN_ID)
    private Long              id;

    public AbstractDTO() {
    }

    public AbstractDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractDTO other = (AbstractDTO) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public abstract String toString();

}
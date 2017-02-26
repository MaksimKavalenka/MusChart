package com.muschart.dto.output;

import javax.validation.constraints.NotNull;

import com.muschart.dto.AbstractDTO;

public class SuggestionOutputDTO extends AbstractDTO {

    private static final long serialVersionUID = -1180231515055740958L;

    @NotNull
    private String            name;

    @NotNull
    private String            entity;

    public SuggestionOutputDTO() {
        super();
    }

    public SuggestionOutputDTO(Long id, String name, String entity) {
        super(id);
        this.name = name;
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + ",entity:" + entity + "]";
    }

}
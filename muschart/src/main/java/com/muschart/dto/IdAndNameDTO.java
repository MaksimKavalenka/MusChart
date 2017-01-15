package com.muschart.dto;

public class IdAndNameDTO extends AbstractDTO {

    private static final long serialVersionUID = 1489848142060411977L;

    private String            name;

    public IdAndNameDTO() {
        super();
    }

    public IdAndNameDTO(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[id:" + super.getId() + ",name:" + name + "]";
    }

}
package by.gsu.jpa.service.dao;

import by.gsu.entity.RoleEntity;

public interface RoleServiceDAO {

    RoleEntity createRole(String name);

    RoleEntity getRoleByName(String name);

    boolean checkRoleName(String name);

}

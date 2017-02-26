package com.muschart.service.database.dao;

import com.muschart.entity.RoleEntity;

public interface RoleDatabaseServiceDAO {

    RoleEntity createRole(String name);

    RoleEntity getRoleByName(String name);

    boolean checkRoleName(String name);

}
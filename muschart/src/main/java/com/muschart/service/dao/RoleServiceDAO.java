package com.muschart.service.dao;

import com.muschart.entity.RoleEntity;

public interface RoleServiceDAO {

    RoleEntity createRole(String name);

    RoleEntity getRoleByName(String name);

    boolean checkRoleName(String name);

}

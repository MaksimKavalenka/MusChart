package com.muschart.service.database.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muschart.entity.RoleEntity;
import com.muschart.jpa.repository.RoleRepository;
import com.muschart.service.database.dao.RoleDatabaseServiceDAO;

@Service("roleDatabaseService")
public class RoleDatabaseService implements RoleDatabaseServiceDAO {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleEntity createRole(String name) {
        RoleEntity role = new RoleEntity(name);
        return roleRepository.save(role);
    }

    @Override
    public RoleEntity getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public boolean checkRoleName(String name) {
        return roleRepository.checkRoleName(name);
    }

}
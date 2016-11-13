package com.muschart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.muschart.entity.RoleEntity;
import com.muschart.jpa.repository.RoleRepository;
import com.muschart.service.dao.RoleServiceDAO;

public class RoleService implements RoleServiceDAO {

    @Autowired
    private RoleRepository repository;

    @Override
    public RoleEntity createRole(String name) {
        RoleEntity role = new RoleEntity(name);
        return repository.save(role);
    }

    @Override
    public RoleEntity getRoleByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public boolean checkRoleName(String name) {
        return repository.checkRoleName(name);
    }

}

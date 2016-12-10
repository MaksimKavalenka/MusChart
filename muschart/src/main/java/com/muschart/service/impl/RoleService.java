package com.muschart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muschart.entity.RoleEntity;
import com.muschart.jpa.repository.RoleRepository;
import com.muschart.service.dao.RoleServiceDAO;

@Service("roleService")
public class RoleService implements RoleServiceDAO {

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
package by.gsu.jpa.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import by.gsu.entity.RoleEntity;
import by.gsu.jpa.repository.RoleRepository;
import by.gsu.jpa.service.dao.RoleServiceDAO;

public class RoleService implements RoleServiceDAO {

    @Autowired
    private RoleRepository repository;

    @Override
    public RoleEntity createRole(final String name) {
        RoleEntity role = new RoleEntity(name);
        return repository.save(role);
    }

    @Override
    public RoleEntity getRoleByName(final String name) {
        return repository.findByName(name);
    }

    @Override
    public boolean checkRoleName(final String name) {
        return repository.checkRoleName(name);
    }

}

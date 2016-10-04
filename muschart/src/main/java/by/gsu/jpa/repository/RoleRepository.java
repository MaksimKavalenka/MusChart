package by.gsu.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import by.gsu.entity.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);

}

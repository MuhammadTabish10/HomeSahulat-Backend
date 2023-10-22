package com.HomeSahulat.repository;

import com.HomeSahulat.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(String roleAdmin);
    Role findByName(String admin);
}

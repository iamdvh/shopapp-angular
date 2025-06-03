package com.iamdvh.shop_app.repositories;

import com.iamdvh.shop_app.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}

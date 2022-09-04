package com.haidukov.jwt.repository;

import com.haidukov.jwt.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);
}

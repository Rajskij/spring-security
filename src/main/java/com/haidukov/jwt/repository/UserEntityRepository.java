package com.haidukov.jwt.repository;

import com.haidukov.jwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);
}

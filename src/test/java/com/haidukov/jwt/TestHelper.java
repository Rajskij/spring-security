package com.haidukov.jwt;

import com.haidukov.jwt.entity.RoleEntity;
import com.haidukov.jwt.entity.UserEntity;

public class TestHelper {

    public static RoleEntity createRoleAdmin() {
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("ROLE_ADMIN");
        return role;
    }

    public static RoleEntity createRoleUser() {
        RoleEntity role = new RoleEntity();
        role.setId(2L);
        role.setName("ROLE_USER");
        return role;
    }

    public static UserEntity createUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setLogin("some.user@mail.com");
        user.setPassword("some_password");
        user.setRoleEntity(createRoleUser());
        return user;
    }
}

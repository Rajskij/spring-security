package com.haidukov.jwt.repository;

import com.haidukov.jwt.entity.RoleEntity;
import com.haidukov.jwt.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.haidukov.jwt.TestHelper.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserEntityRepositoryTest {

    @Autowired
    private UserEntityRepository entityRepository;

    @Test
    public void givenEntity_whenSave_thenGetOk() {
        UserEntity expectedUser = createUser();

        entityRepository.save(expectedUser);

        UserEntity actualUser = entityRepository.findById(1L).orElse(null);

        assertNotNull(actualUser);
        assertEquals(actualUser.getId(), expectedUser.getId());
        assertEquals(actualUser.getLogin(), expectedUser.getLogin());
        assertEquals(actualUser.getPassword(), expectedUser.getPassword());
        assertEquals(actualUser.getRoleEntity(), expectedUser.getRoleEntity());
    }
}

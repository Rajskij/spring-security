package com.haidukov.jwt.repository;

import com.haidukov.jwt.entity.RoleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static com.haidukov.jwt.TestHelper.createRoleAdmin;
import static com.haidukov.jwt.TestHelper.createRoleUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RoleEntityRepositoryIT {

    @Autowired
    private RoleEntityRepository entityRepository;

    @Test
    @Transactional
    public void givenEntity_whenSave_thenGetOk() {
        RoleEntity expectedRole = createRoleAdmin();

        RoleEntity actualRole = entityRepository.findById(1L).orElse(null);

        assertNotNull(actualRole);
        assertEquals(actualRole.getId(), expectedRole.getId());
        assertEquals(actualRole.getName(), expectedRole.getName());
    }
}

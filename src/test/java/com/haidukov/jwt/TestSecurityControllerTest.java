package com.haidukov.jwt;

import com.haidukov.jwt.controller.TestSecurityController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class TestSecurityControllerTest {

    @InjectMocks
    private TestSecurityController testSecurityController;

    @Test
    public void testGetAdmin() {
        String response = testSecurityController.getAdmin();

        assertNotNull(response);
        assertEquals(response, "Hi admin");
    }

    @Test
    public void testGetUser() {
        String response = testSecurityController.getUser();

        assertNotNull(response);
        assertEquals(response, "Hi user");
    }
}

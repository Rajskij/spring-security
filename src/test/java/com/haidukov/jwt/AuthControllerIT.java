package com.haidukov.jwt;

import com.haidukov.jwt.config.jwt.JwtProvider;
import com.haidukov.jwt.controller.AuthController;
import com.haidukov.jwt.dto.AuthRequest;
import com.haidukov.jwt.dto.AuthResponse;
import com.haidukov.jwt.dto.RegistrationRequest;
import com.haidukov.jwt.entity.UserEntity;
import com.haidukov.jwt.repository.UserEntityRepository;
import com.haidukov.jwt.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.haidukov.jwt.TestHelper.createRoleUser;
import static com.haidukov.jwt.TestHelper.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class AuthControllerIT {

    private static final String SOME_EMAIL = "some.user@mail.com";

    private static final String ANOTHER_EMAIL = "another.user@mail.com";
    private static final String SOME_PASSWORD = "some_password";

    @Autowired
    private AuthController authController;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserEntityRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    private OAuth2User principal = new OAuth2User() {
        @Override
        public Map<String, Object> getAttributes() {
            return new HashMap<>(Collections.singletonMap("email", SOME_EMAIL));
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };

    @BeforeEach
    public void setup() {
        UserEntity user = createUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Test
    public void testOauth2ExistingUser() {
        UserEntity user = new UserEntity();
        user.setLogin(SOME_EMAIL);
        user.setPassword(SOME_PASSWORD);

        Map<String, String> attribute = authController.user(principal);

        assertNotNull(attribute);

        UserEntity savedUser = repository.findByLogin(SOME_EMAIL);

        assertNotNull(savedUser);
        assertEquals(savedUser.getLogin(), user.getLogin());
        assertEquals(savedUser.getRoleEntity(), createRoleUser());

        jwtProvider.validateToken(attribute.get("token"));
    }

    @Test
    public void testRegisterUser() {
        RegistrationRequest request = new RegistrationRequest();
        request.setLogin(ANOTHER_EMAIL);
        request.setPassword(SOME_PASSWORD);
        request.setRole("ROLE_ADMIN");

        String response = authController.registerUser(request);

        assertNotNull(response);
        assertEquals(response, "Ok");
    }

    @Test
    public void testAuthUser() {
        AuthRequest request = new AuthRequest();
        request.setLogin(SOME_EMAIL);
        request.setPassword(SOME_PASSWORD);

        AuthResponse response = authController.auth(request);

        assertNotNull(response);
        jwtProvider.validateToken(response.getToken());
    }
}

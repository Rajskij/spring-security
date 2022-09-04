package com.haidukov.jwt.controller;

import com.haidukov.jwt.config.jwt.JwtProvider;
import com.haidukov.jwt.dto.AuthRequest;
import com.haidukov.jwt.dto.AuthResponse;
import com.haidukov.jwt.dto.RegistrationRequest;
import com.haidukov.jwt.entity.UserEntity;
import com.haidukov.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/user")
    public Map<String, String> user(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        Map<String, String> attribute = new HashMap<>(Collections.singletonMap("email", email));
        UserEntity userEntity = userService.findByLogin(email);

        if (userEntity == null) {
            UserEntity user = new UserEntity();
            user.setLogin(email);
            user.setPassword("default_password");
            userService.saveUser(user, null);
        }

        String token = jwtProvider.generateToken(email);
        attribute.put("token", token);
        return attribute;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserEntity user = new UserEntity();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveUser(user, registrationRequest.getRole());
        return "Ok";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        UserEntity userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getLogin());
        return new AuthResponse(token);
    }
}

package com.haidukov.jwt.actuator;

import com.haidukov.jwt.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class InfoService implements InfoContributor {

    @Autowired
    UserEntityRepository userRepository;

    @Override
    public void contribute(Info.Builder builder) {
        HashMap<String, Integer> userCount = new HashMap<>();
        Integer count = userRepository.findAll().size();
        userCount.put("currentUsers", count);
        builder.withDetail("userMetrics", userCount);
    }
}

package com.haidukov.jwt.config;

import com.haidukov.jwt.config.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                // disable csrf and httpBasic authentication
//                .httpBasic().disable()
//                .csrf().disable()
//                // sessionManagement config
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                // all requests wil be authorized
//                .authorizeRequests()
//                    .antMatchers("/admin/*").hasRole("ADMIN")
//                    .antMatchers("/user").permitAll()
//                    .antMatchers("/register", "/auth").permitAll()
//                    .antMatchers("/", "/error", "/webjars/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                // add filter to check is user permission by jwt token
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                    .antMatchers("/admin/*").hasRole("ADMIN")
                    .antMatchers("/user").permitAll()
                    .antMatchers("/h2/*").permitAll()
                    .antMatchers("/register", "/auth").permitAll()
                    .antMatchers("/", "/error", "/webjars/**").permitAll()
                    .antMatchers("/actuator/prometheus").permitAll()
//                    .antMatchers("/actuator/**").hasAnyAuthority("ROLE_ADMIN")
                    .antMatchers("/actuator").permitAll()
                    .antMatchers("/actuator/*").permitAll()
                .anyRequest().authenticated()
                .and()
                    .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                    .logout().logoutSuccessUrl("/").permitAll()
                .and()
                    .oauth2Login()
                .and()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

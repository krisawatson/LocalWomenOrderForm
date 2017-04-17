/*
 * Kris Watson Copyright (c) 2017.
 */

package com.kricko.security;

import com.kricko.constants.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private
    DataSource dataSource;

    @Autowired
    private
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        final String findUserQuery = "SELECT username,password,enabled "
                + "FROM user WHERE username = ?";
        final String findRoles = "SELECT u.username, r.name FROM user u "
                + "JOIN role r ON u.role_id = r.id WHERE u.username =  ?";

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(findUserQuery)
                .authoritiesByUsernameQuery(findRoles);
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/user/**", "/publication/**").hasAnyAuthority(Roles.ADMIN.toString())
                .antMatchers("/order/**/delete").hasAnyAuthority(Roles.SUPER_USER.toString())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .permitAll();
        http.csrf().disable();
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

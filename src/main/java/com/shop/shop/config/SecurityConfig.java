package com.shop.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication().dataSource(dataSource);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/cart/**").authenticated()
                    .antMatchers("/order/**").authenticated()
                    //.antMatchers("/admin/**").authenticated()
                    .and()
                    .formLogin()
                        .loginPage("/login/showLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .defaultSuccessUrl("/product/recommended", true)
                        .permitAll()
                    .and()
                    .logout()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                    .and()
                    .exceptionHandling().accessDeniedPage("/login/showLoginPage");
    }


}










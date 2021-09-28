package com.shop.shop.config;

import com.shop.shop.filter.CustomAuthenticationFilter;
import com.shop.shop.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication().dataSource(dataSource);
        }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Ignorowanie dostępu do widoków
        web.ignoring().antMatchers( "/*.html", "/**/*.html", "/**/*.css", "/**/*.js","/images/**", "/registration/**");
    }


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.authorizeRequests()
                    /*.antMatchers("/cart/**").authenticated()
                    .antMatchers("/order/**").authenticated()
                    .antMatchers("/admin/**").authenticated()
                    .antMatchers("/product/**").permitAll()*/
                    .and()
                    .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/product/recommended", true)
                        .permitAll()
                    .and()
                    .logout()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                    .deleteCookies("access_token")
                    .and()
                    .exceptionHandling().accessDeniedPage("/login")
                    .and()
                    .csrf().disable();

            http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
           // http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }


}










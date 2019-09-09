package io.levvel.app.config;

import io.levvel.app.registration.user.CustomUser;
import io.levvel.app.registration.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private DataSource dataSource;
    @Autowired private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
          .antMatchers("/login", "/oauth/authorize")
                .and()
          .authorizeRequests()
          .anyRequest().authenticated()
          .and()
          .formLogin().permitAll();
    }

    @Bean
    public CustomUser customUser(){
        return new CustomUser();
    }

    @Bean
    public ApplicationEventPublisher eventPublisher(){
        ApplicationEventPublisher ePub = o -> { };
        return ePub;
    }
 
    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(this.dataSource).and().userDetailsService(customUserDetailsService);//.withDefaultSchema();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){ 
        return new BCryptPasswordEncoder(); 
    }
}

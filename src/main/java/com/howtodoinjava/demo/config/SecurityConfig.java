package com.howtodoinjava.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private DataSource dataSource;
 
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
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager mgr = new JdbcUserDetailsManager();
        mgr.setDataSource(dataSource);
        return mgr;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(this.dataSource)
                .withDefaultSchema()
                .withUser("ryan").password(passwordEncoder().encode("passwordryan")).roles("USER")
                .and()
                .withUser("matt").password(passwordEncoder().encode("passwordmatt")).roles("USER")
                .and()
                .withUser("han").password(passwordEncoder().encode("passwordhan")).roles("USER")
                .and()
                .withUser("rob").password(passwordEncoder().encode("passwordrob")).roles("USER")
                .and()
                .withUser("medge").password(passwordEncoder().encode("medge")).roles("ADMIN");
        	//.inMemoryAuthentication()
            //.withUser("humptydumpty")
            //.password(passwordEncoder().encode("123456"))
            //.roles("USER");
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

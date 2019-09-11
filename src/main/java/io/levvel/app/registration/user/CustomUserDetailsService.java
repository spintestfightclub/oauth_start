package io.levvel.app.registration.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class CustomUserDetailsService extends JdbcUserDetailsManager{
    @Autowired private CustomUserRepository customUserRepository;

    public CustomUserDetailsService(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = customUserRepository.findByUsername(username);
        if(customUser == null){
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(customUser);
    }

}

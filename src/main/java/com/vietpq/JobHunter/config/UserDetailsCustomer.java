package com.vietpq.JobHunter.config;

import com.vietpq.JobHunter.entity.User;
import com.vietpq.JobHunter.service.user.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("userDetailsService")
public class UserDetailsCustomer implements UserDetailsService {
    private final UserService userService;

    public UserDetailsCustomer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = this.userService.handleGetUserByUsername(username);
       if(user == null){
           throw new UsernameNotFoundException("Username/password khong hop le");
       }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

    }
}

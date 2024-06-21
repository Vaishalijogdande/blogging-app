package com.blogging_app.security;

import com.blogging_app.entity.User;
import com.blogging_app.exception.ResourceNotFoundException;
import com.blogging_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = this.userRepository.findByEmail(username).orElseThrow(() ->
               new ResourceNotFoundException("user", "email"+username,0L));

        return user;
    }
}

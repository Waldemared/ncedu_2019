package com.ncedu.nc_edu.security;

import com.ncedu.nc_edu.exceptions.EntityDoesNotExistsException;
import com.ncedu.nc_edu.models.User;
import com.ncedu.nc_edu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s).orElseThrow(() -> new EntityDoesNotExistsException("User"));

        return new CustomUserDetails(user);
    }


}

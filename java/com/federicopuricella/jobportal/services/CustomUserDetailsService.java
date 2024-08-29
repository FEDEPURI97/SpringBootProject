package com.federicopuricella.jobportal.services;

import com.federicopuricella.jobportal.entity.Users;
import com.federicopuricella.jobportal.repository.UsersRepository;
import com.federicopuricella.jobportal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=usersRepository.findByEmail(username).orElseThrow(()->new
                UsernameNotFoundException("Could not found user"));
        return new CustomUserDetails(users);
    }
}

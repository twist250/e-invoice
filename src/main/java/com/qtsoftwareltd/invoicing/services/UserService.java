package com.qtsoftwareltd.invoicing.services;

import com.qtsoftwareltd.invoicing.entities.User;
import com.qtsoftwareltd.invoicing.exceptions.ResourceNotFoundException;
import com.qtsoftwareltd.invoicing.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return findUserByEmail(username);
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

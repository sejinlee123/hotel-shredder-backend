package com.leshredder.hotel_booking.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leshredder.hotel_booking.entities.User;
import com.leshredder.hotel_booking.exceptions.NotFoundException;
import com.leshredder.hotel_booking.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new NotFoundException("User Email Not Found"));

        return AuthUser.builder()
            .user(user)
            .build();
    }

}

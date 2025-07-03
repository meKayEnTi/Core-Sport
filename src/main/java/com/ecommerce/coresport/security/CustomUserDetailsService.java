package com.ecommerce.coresport.security;

import com.ecommerce.coresport.constant.ErrorMessages;
import com.ecommerce.coresport.entity.User;
import com.ecommerce.coresport.exception.ResourceNotFoundException;
import com.ecommerce.coresport.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        return new CustomUserDetails(user);
    }
}

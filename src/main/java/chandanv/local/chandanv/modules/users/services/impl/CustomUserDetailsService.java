package chandanv.local.chandanv.modules.users.services.impl;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;

import chandanv.local.chandanv.modules.users.repositories.UserRepository;
import chandanv.local.chandanv.modules.users.entities.User;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service 
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new UsernameNotFoundException("User khong ton tai"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );

    }

}

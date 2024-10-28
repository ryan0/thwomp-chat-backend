package dev.rmiller.thwompchatbackend.security;

import dev.rmiller.thwompchatbackend.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ThwompUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    ThwompUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ThwompUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findSecurityDetails(username);
        return new ThwompUserDetails(user);
    }
}

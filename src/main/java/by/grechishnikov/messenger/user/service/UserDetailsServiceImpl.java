package by.grechishnikov.messenger.user.service;

import by.grechishnikov.messenger.user.entity.User;
import by.grechishnikov.messenger.user.repository.UserRepository;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else if (user.isBlocked()) {
            throw new LockedException(username + " is locked");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), emptyList());
    }

}

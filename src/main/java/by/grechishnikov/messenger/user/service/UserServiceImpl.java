package by.grechishnikov.messenger.user.service;

import by.grechishnikov.messenger.common.service.AbstractServiceImpl;
import by.grechishnikov.messenger.user.entity.User;
import by.grechishnikov.messenger.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveOrUpdate(User user) {
        if (user.getId() == 0) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setUpdated();
        }
        return super.saveOrUpdate(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findByCurrentRefreshToken(String refreshToken) {
        return userRepository.findByCurrentRefreshToken(refreshToken);
    }

    @Override
    public Page<User> search(String name, Pageable pageable) {
        return StringUtils.isEmpty(name)
                ? userRepository.findAll(pageable)
                : userRepository.findAllByNameLike("%" + name + "%", pageable);
    }

    @Override
    public void failedLoginAttemptHandler(String login) {
        User user = findByLogin(login);
        if (user != null && !user.isBlocked()) {
            if (user.getFailedLoginAttempts() < 5) {
                user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            } else {
                user.setBlocked();
            }
            saveOrUpdate(user);
        }
    }

    @Override
    public boolean isLoginExist(String login) {
        return userRepository.findIdByLogin(login) > 0;
    }

}

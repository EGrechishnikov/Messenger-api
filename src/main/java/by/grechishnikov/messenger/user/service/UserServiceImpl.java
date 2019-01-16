package by.grechishnikov.messenger.user.service;

import by.grechishnikov.messenger.attachment.entity.Attachment;
import by.grechishnikov.messenger.common.service.AbstractServiceImpl;
import by.grechishnikov.messenger.user.entity.User;
import by.grechishnikov.messenger.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    public void convertAndSave(String json, MultipartFile avatar) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(json, User.class);
        if (avatar != null && !avatar.isEmpty()) {
            if (user.getAttachment() == null) {
                user.setAttachment(new Attachment(avatar.getBytes()));
            } else {
                user.getAttachment().setContent(avatar.getBytes());
            }
        }
        saveOrUpdate(user);
    }

    @Override
    public User saveOrUpdate(User user) {
        if (user.getId() == 0) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(findById(user.getId()).getPassword());
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
    public Page<User> search(String name, String currentUserLogin, Pageable pageable) {
        System.out.println(currentUserLogin);
        System.out.println("%" + name + "%");
        System.out.println(userRepository.test("user","%123%",pageable).getContent());
        return StringUtils.isEmpty(name)
                ? userRepository.findAllByLoginNot(currentUserLogin, pageable)
                : userRepository.findAllByLoginIsNotAndNameLike(currentUserLogin, "%" + name + "%", pageable);
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

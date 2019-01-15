package by.grechishnikov.messenger.user.service;

import by.grechishnikov.messenger.common.service.AbstractService;
import by.grechishnikov.messenger.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface UserService extends AbstractService<User> {

    void convertAndSave(String json, MultipartFile avatar) throws Exception;

    User findByLogin(String login);

    User findByCurrentRefreshToken(String refreshToken);

    void failedLoginAttemptHandler(String login);

    boolean isLoginExist(String login);

    Page<User> search(String name, Pageable pageable);

}

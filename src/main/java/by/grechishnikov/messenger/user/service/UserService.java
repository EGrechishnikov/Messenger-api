package by.grechishnikov.messenger.user.service;

import by.grechishnikov.messenger.common.service.AbstractService;
import by.grechishnikov.messenger.user.entity.User;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface UserService extends AbstractService<User> {

    User findByLogin(String login);

    User findByCurrentRefreshToken(String refreshToken);

    void failedLoginAttemptHandler(String login);

    boolean isLoginExist(String login);

}

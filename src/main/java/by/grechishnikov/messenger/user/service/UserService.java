package by.grechishnikov.messenger.user.service;

import by.grechishnikov.messenger.common.service.AbstractService;
import by.grechishnikov.messenger.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface UserService extends AbstractService<User> {

    User convertAndSave(String json, MultipartFile avatar) throws Exception;

    User findByLogin(String login);

    User findByCurrentRefreshToken(String refreshToken);

    void failedLoginAttemptHandler(String login);

    boolean isLoginExist(String login);

    Page<User> searchAllByNameAndCurrentUserLogin(String name, String currentUserLogin, Pageable pageable);

    Set<User> findAllByChatIdAndUserId(int chatId, int userId);

}

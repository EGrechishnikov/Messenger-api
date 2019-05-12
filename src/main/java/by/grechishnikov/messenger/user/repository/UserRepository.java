package by.grechishnikov.messenger.user.repository;

import by.grechishnikov.messenger.common.repository.AbstractRepository;
import by.grechishnikov.messenger.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface UserRepository extends AbstractRepository<User> {

    User findByLogin(String login);

    User findByCurrentRefreshToken(String refreshToken);

    @Query("SELECT id FROM User WHERE login = :login")
    Optional<Integer> findIdByLogin(@Param("login") String login);

    Page<User> findAllByLoginIsNot(String currentUserLogin, Pageable pageable);

    Page<User> findAllByLoginIsNotAndNameLike(@Param("login") String login, @Param("name") String name, Pageable pageable);

    @Query(value = "SELECT * FROM t_user u INNER JOIN t_chat_user c ON u.id = c.user_id WHERE c.chat_id = :chatId AND u.id <> :userId", nativeQuery = true)
    Set<User> findAllByChatIdAndUserId(@Param("chatId") int chatId, @Param("userId") int userId);

}

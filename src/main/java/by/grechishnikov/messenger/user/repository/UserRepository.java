package by.grechishnikov.messenger.user.repository;

import by.grechishnikov.messenger.common.repository.AbstractRepository;
import by.grechishnikov.messenger.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface UserRepository extends AbstractRepository<User> {

    User findByLogin(String login);

    User findByCurrentRefreshToken(String refreshToken);

    @Query("SELECT id FROM User WHERE login = :login")
    int findIdByLogin(@Param("login") String login);

    Page<User> findAllByLoginNot(String currentUserLogin, Pageable pageable);

    Page<User> findAllByLoginIsNotAndNameLike(@Param("login") String login, @Param("name") String name, Pageable pageable);

    @Query("FROM User u WHERE u.login <> :login AND u.name LIKE :name")
    Page<User> test(@Param("login") String login, @Param("name") String name, Pageable pageable);

}

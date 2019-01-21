package by.grechishnikov.messenger.chat.repository;

import by.grechishnikov.messenger.chat.entity.Chat;
import by.grechishnikov.messenger.common.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface ChatRepository extends AbstractRepository<Chat> {

    @Query(value =
            "SELECT * FROM t_chat c INNER JOIN t_chat_user u ON c.id = u.chat_id WHERE u.user_id = :user_id", nativeQuery = true)
    List<Chat> findAllByUserId(@Param("user_id") int userId);

}

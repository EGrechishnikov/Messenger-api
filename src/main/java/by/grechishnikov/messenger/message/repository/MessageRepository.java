package by.grechishnikov.messenger.message.repository;

import by.grechishnikov.messenger.common.repository.AbstractRepository;
import by.grechishnikov.messenger.message.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface MessageRepository extends AbstractRepository<Message>{

    Page<Message> findAllByChatId(int chartId, Pageable pageable);

}

package by.grechishnikov.messenger.message.service;

import by.grechishnikov.messenger.common.service.AbstractService;
import by.grechishnikov.messenger.message.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface MessageService extends AbstractService<Message> {

    void sendMessage(Message message);

    Page<Message> findAllByChatId(int chartId, Pageable pageable);

}

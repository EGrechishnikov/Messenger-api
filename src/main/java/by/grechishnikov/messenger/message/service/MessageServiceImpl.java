package by.grechishnikov.messenger.message.service;

import by.grechishnikov.messenger.common.service.AbstractServiceImpl;
import by.grechishnikov.messenger.message.entity.Message;
import by.grechishnikov.messenger.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class MessageServiceImpl extends AbstractServiceImpl<Message> implements MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        super(messageRepository);
        this.messageRepository = messageRepository;
    }

    @Override
    public Page<Message> findAllByChatId(int chartId, Pageable pageable) {
        return messageRepository.findAllByChatId(chartId, pageable);
    }

}

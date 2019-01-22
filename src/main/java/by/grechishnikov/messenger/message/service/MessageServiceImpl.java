package by.grechishnikov.messenger.message.service;

import by.grechishnikov.messenger.chat.service.ChatService;
import by.grechishnikov.messenger.common.service.AbstractServiceImpl;
import by.grechishnikov.messenger.message.entity.Message;
import by.grechishnikov.messenger.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class MessageServiceImpl extends AbstractServiceImpl<Message> implements MessageService {

    private MessageRepository messageRepository;
    private SimpMessagingTemplate simpMessagingTemplate;
    private ChatService chatService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,
                              SimpMessagingTemplate simpMessagingTemplate,
                              ChatService chatService) {
        super(messageRepository);
        this.messageRepository = messageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatService = chatService;
    }

    @Override
    public void sendMessage(Message message) {
        saveOrUpdate(message);
        chatService.findById(message.getChatId())
                .getUsers()
                .stream()
                .filter(user -> user.getId() != message.getFromUserId())
                .forEach(user -> simpMessagingTemplate.convertAndSendToUser(user.getLogin(), "/chat", message));
    }

    @Override
    public Page<Message> findAllByChatId(int chartId, Pageable pageable) {
        return messageRepository.findAllByChatId(chartId, pageable);
    }

}

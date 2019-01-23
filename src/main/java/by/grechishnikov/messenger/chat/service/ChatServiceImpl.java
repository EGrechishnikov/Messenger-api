package by.grechishnikov.messenger.chat.service;

import by.grechishnikov.messenger.chat.entity.Chat;
import by.grechishnikov.messenger.chat.repository.ChatRepository;
import by.grechishnikov.messenger.common.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class ChatServiceImpl extends AbstractServiceImpl<Chat> implements ChatService {

    private ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        super(chatRepository);
        this.chatRepository = chatRepository;
    }

    @Override
    public List<Chat> findAllByUserId(int userId) {
        return chatRepository.findAllByUserId(userId);
    }

}

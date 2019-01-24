package by.grechishnikov.messenger.chat.service;

import by.grechishnikov.messenger.chat.entity.Chat;
import by.grechishnikov.messenger.chat.repository.ChatRepository;
import by.grechishnikov.messenger.common.service.AbstractServiceImpl;
import by.grechishnikov.messenger.user.entity.User;
import by.grechishnikov.messenger.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class ChatServiceImpl extends AbstractServiceImpl<Chat> implements ChatService {

    private ChatRepository chatRepository;
    private UserService userService;

    public ChatServiceImpl(ChatRepository chatRepository, UserService userService) {
        super(chatRepository);
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @Override
    public Chat saveOrUpdate(Chat chat, int userId) {
        chat = super.saveOrUpdate(chat);
        chat.setUsers(userService.findAllByChatId(chat.getId(), userId));
        return chat;
    }

    @Override
    public List<Chat> findAllByUserId(int userId) {
        return chatRepository.findAllByUserId(userId);
    }

}

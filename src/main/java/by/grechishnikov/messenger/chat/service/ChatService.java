package by.grechishnikov.messenger.chat.service;

import by.grechishnikov.messenger.chat.entity.Chat;
import by.grechishnikov.messenger.common.service.AbstractService;

import java.util.List;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface ChatService extends AbstractService<Chat> {

    Chat saveOrUpdate(Chat chat, int userId);

    List<Chat> findAllByUserId(int userId);

}

package by.grechishnikov.messenger.websocket.controller;

import by.grechishnikov.messenger.message.entity.Message;
import by.grechishnikov.messenger.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
/**
 * @author - Evgeniy Grechishnikov
 */
@Controller
@SuppressWarnings("unused")
public class SocketController {

    private MessageService messageService;

    @Autowired
    public SocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat/message")
    public void sendSpecific(@Payload Message message) {
        messageService.sendMessage(message);
    }

}

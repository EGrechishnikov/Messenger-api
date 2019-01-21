package by.grechishnikov.messenger.websocket.controller;

import by.grechishnikov.messenger.message.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.security.Principal;
/**
 * @author - Evgeniy Grechishnikov
 */
@Controller
@SuppressWarnings("unused")
public class SocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public SocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat/message")
    public void sendSpecific(@Payload Message message, @Header("simpSessionId") String sessionId, Principal principal) throws Exception {
        System.out.println(message);
//        for (int i=0; i<5; i++) {
            simpMessagingTemplate.convertAndSendToUser("user", "/chat", message);
//            Thread.sleep(1000);
//        }
    }

}

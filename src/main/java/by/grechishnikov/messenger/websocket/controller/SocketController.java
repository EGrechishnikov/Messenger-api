package by.grechishnikov.messenger.websocket.controller;

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
    public void sendSpecific(@Payload String msg, @Header("simpSessionId") String sessionId, Principal principal) throws Exception {
        for (int i=0; i<5; i++) {
            simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/chat", principal.getName() + ":" + msg);
            Thread.sleep(1000);
        }
    }

}

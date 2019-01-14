package by.grechishnikov.messenger.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class SocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Example of sending message to specific user using 'convertAndSendToUser()' and '/queue'
     */
    @MessageMapping("/chat/message")
    public void sendSpecific(@Payload String msg, @Header("simpSessionId") String sessionId, Principal principal) throws Exception {
        for (int i=0; i<10; i++) {
            simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/chat", msg);
            Thread.sleep(1500);
        }
    }

}

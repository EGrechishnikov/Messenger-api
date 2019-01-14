package by.grechishnikov.messenger.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class SocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/secured/chat")
    @SendTo("/secured/history")
    public String sendAll(String msg) throws Exception {
        System.out.println(msg);
//        OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
        return msg;
    }

    /**
     * Example of sending message to specific user using 'convertAndSendToUser()' and '/queue'
     */
    @MessageMapping("/secured/room")
    public void sendSpecific(@Payload String msg, @Header("simpSessionId") String sessionId) throws Exception {
        System.out.println(msg);
        System.out.println(sessionId);
//        OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
        System.out.println(simpMessagingTemplate.getUserDestinationPrefix());
        System.out.println(StringUtils.replace("login", "/", "%2F"));
        System.out.println(simpMessagingTemplate.getUserDestinationPrefix() + "login" + "/secured/user/queue/specific-user");
        simpMessagingTemplate.convertAndSendToUser("login", "/secured/user/queue/specific-user", msg);
    }
}

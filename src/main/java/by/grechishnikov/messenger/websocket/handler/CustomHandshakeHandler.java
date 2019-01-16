package by.grechishnikov.messenger.websocket.handler;


import by.grechishnikov.messenger.websocket.dto.StompPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

/**
 * @author - Evgeniy Grechishnikov
 */
public class CustomHandshakeHandler extends DefaultHandshakeHandler {



    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        try {
            System.out.println(attributes.keySet());
            System.out.println(request.getHeaders().keySet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StompPrincipal(UUID.randomUUID().toString());
    }

}

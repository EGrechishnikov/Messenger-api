package by.grechishnikov.messenger.websocket.handler;

import by.grechishnikov.messenger.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author - Evgeniy Grechishnikov
 */
public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    private TokenService tokenService;

    public CustomHandshakeInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        try {
            String accessToken = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getParameter("access_token");
            String login = tokenService.getLoginFromToken(accessToken);
            map.put("login", login);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }

}

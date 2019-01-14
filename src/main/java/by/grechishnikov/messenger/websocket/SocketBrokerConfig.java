package by.grechishnikov.messenger.websocket;

import by.grechishnikov.messenger.common.ApplicationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    private static final String FRONT_END_SERVER_ADDRESS =
            ApplicationProperty.getStringProperty("front-end.address");

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/secured/history", "/secured/user/queue/specific-user");
        config.setApplicationDestinationPrefixes("/spring-security-mvc-socket");
        config.setUserDestinationPrefix("/secured/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/secured/chat")
                .setAllowedOrigins(FRONT_END_SERVER_ADDRESS).withSockJS();
        registry.addEndpoint("/secured/room")
                .setAllowedOrigins(FRONT_END_SERVER_ADDRESS).withSockJS();
    }

}


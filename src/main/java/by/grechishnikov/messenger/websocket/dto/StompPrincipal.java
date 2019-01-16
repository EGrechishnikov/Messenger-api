package by.grechishnikov.messenger.websocket.dto;

import java.security.Principal;

/**
 * @author - Evgeniy Grechishnikov
 */
public class StompPrincipal implements Principal {

    private String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}

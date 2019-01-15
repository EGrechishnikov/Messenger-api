package by.grechishnikov.messenger.websocket;

import java.security.Principal;

/**
 * @author - Evgeniy Grechishnikov
 */
public class StompPrincipal implements Principal {

    private String name;

    StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}

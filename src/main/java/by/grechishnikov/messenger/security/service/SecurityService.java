package by.grechishnikov.messenger.security.service;

import by.grechishnikov.messenger.security.dto.TokenDTO;
import by.grechishnikov.messenger.security.dto.CredentialsDTO;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface SecurityService {

    void logout(String currentRefreshToken);

    TokenDTO registration(CredentialsDTO credentials);

}

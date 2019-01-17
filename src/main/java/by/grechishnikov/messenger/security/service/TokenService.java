package by.grechishnikov.messenger.security.service;

import by.grechishnikov.messenger.security.dto.TokenDTO;
import by.grechishnikov.messenger.user.entity.User;

/**
 * @author - Evgeniy Grechishnikov
 */
public interface TokenService {

    String createAccessToken(String login);

    String createRefreshToken(User user);

    TokenDTO refreshTokens(String refreshToken);

    String getLoginFromToken(String token);

}

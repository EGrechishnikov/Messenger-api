package by.grechishnikov.messenger.security.service;

import by.grechishnikov.messenger.common.ApplicationProperty;
import by.grechishnikov.messenger.security.dto.TokenDTO;
import by.grechishnikov.messenger.user.entity.User;
import by.grechishnikov.messenger.user.service.UserService;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final String SECRET =
            ApplicationProperty.getStringProperty("security.token.secret_key");
    private static final int ACCEPT_TOKEN_EXPIRATION_TIME =
            ApplicationProperty.getIntegerProperty("security.token.access.expiration_time");
    private static final int REFRESH_TOKEN_EXPIRATION_TIME =
            ApplicationProperty.getIntegerProperty("security.token.refresh.expiration_time");
    private UserService userService;

    @Autowired
    public TokenServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String createAccessToken(String login) {
        return JWT.create().withSubject(login).withExpiresAt(
                new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(ACCEPT_TOKEN_EXPIRATION_TIME)))
                .sign(HMAC512(SECRET.getBytes()));
    }

    @Override
    public String createRefreshToken(User user) {
        String refreshToken = JWT.create().withSubject(user.getLogin()).withExpiresAt(
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(REFRESH_TOKEN_EXPIRATION_TIME)))
                .sign(HMAC512(SECRET.getBytes()));
        user.setCurrentRefreshToken(refreshToken);
        userService.saveOrUpdate(user);
        return refreshToken;
    }

    @Override
    public TokenDTO refreshTokens(String refreshToken) throws BadCredentialsException {
        User user = userService.findByCurrentRefreshToken(refreshToken);
        if (user == null || !user.getCurrentRefreshToken().equals(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }
        return new TokenDTO(createAccessToken(user.getLogin()), createRefreshToken(user));
    }

}

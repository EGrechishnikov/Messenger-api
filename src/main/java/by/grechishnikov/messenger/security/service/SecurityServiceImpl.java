package by.grechishnikov.messenger.security.service;

import by.grechishnikov.messenger.security.dto.TokenDTO;
import by.grechishnikov.messenger.security.dto.CredentialsDTO;
import by.grechishnikov.messenger.user.entity.User;
import by.grechishnikov.messenger.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private UserService userService;
    private TokenService tokenService;

    @Autowired
    public SecurityServiceImpl(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public void logout(String currentRefreshToken) {
        User user = userService.findByCurrentRefreshToken(currentRefreshToken);
        if (user != null) {
            user.setCurrentRefreshToken("");
            userService.saveOrUpdate(user);
        }
    }

    @Override
    public TokenDTO registration(CredentialsDTO credentials) {
        User user = userService.saveOrUpdate(new User(credentials.getLogin(), credentials.getPassword()));
        return new TokenDTO(
                tokenService.createAccessToken(user.getLogin()),
                tokenService.createRefreshToken(user),
                user.getLogin());
    }

}

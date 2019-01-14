package by.grechishnikov.messenger.security.filter;

import by.grechishnikov.messenger.security.dto.TokenDTO;
import by.grechishnikov.messenger.security.service.TokenService;
import by.grechishnikov.messenger.user.entity.User;
import by.grechishnikov.messenger.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author - Evgeniy Grechishnikov
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private TokenService tokenService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   UserService userService,
                                   TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        User user = null;
        try {
            user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AuthenticationException e) {
            if (user != null) {
                userService.failedLoginAttemptHandler(user.getLogin());
            }
            throw e;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException {
        String login = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        User user = userService.findByLogin(login);
        String accessToken = tokenService.createAccessToken(login);
        String refreshToken = tokenService.createRefreshToken(user);
        TokenDTO tokenDTO = new TokenDTO(accessToken, refreshToken, user.getId());
        user.setCurrentRefreshToken(refreshToken);
        user.setFailedLoginAttempts(0);
        userService.saveOrUpdate(user);
        res.getWriter().write(objectMapper.writeValueAsString(tokenDTO));
    }

}

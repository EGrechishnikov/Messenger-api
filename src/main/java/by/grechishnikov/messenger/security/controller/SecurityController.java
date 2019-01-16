package by.grechishnikov.messenger.security.controller;

import by.grechishnikov.messenger.security.dto.TokenDTO;
import by.grechishnikov.messenger.security.service.SecurityService;
import by.grechishnikov.messenger.security.service.TokenService;
import by.grechishnikov.messenger.security.dto.CredentialsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author - Evgeniy Grechishnikov
 */
@RestController
@RequestMapping("/security")
public class SecurityController {

    private TokenService tokenService;
    private SecurityService securityService;

    @Autowired
    public SecurityController(TokenService tokenService, SecurityService securityService) {
        this.tokenService = tokenService;
        this.securityService = securityService;
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenDTO> refreshTokens(@RequestBody String refreshToken) {
        try {
            return new ResponseEntity<>(tokenService.refreshTokens(refreshToken), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/logout")
    public void logout(@RequestBody String currentRefreshToken) {
        securityService.logout(currentRefreshToken);
    }

    @PostMapping("/user/registration")
    public ResponseEntity<TokenDTO> registration(@RequestBody CredentialsDTO credentials) {
        try {
            return new ResponseEntity<>(securityService.registration(credentials), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

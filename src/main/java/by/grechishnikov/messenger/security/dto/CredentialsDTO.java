package by.grechishnikov.messenger.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author - Evgeniy Grechishnikov
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CredentialsDTO {

    private String login;
    private String password;

}

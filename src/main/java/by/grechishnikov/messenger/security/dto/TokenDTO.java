package by.grechishnikov.messenger.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author - Evgeniy Grechishnikov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

    private String accessToken;
    private String refreshToken;
    private int userId;

}

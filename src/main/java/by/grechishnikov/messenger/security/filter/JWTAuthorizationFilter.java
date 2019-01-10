package by.grechishnikov.messenger.security.filter;

import by.grechishnikov.messenger.common.ApplicationProperty;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author - Evgeniy Grechishnikov
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String HEADER_NAME =
            ApplicationProperty.getStringProperty("security.header.authorization");
    private static final String SECRET =
            ApplicationProperty.getStringProperty("security.token.secret_key");
    private static final String TOKEN_PREFIX =
            ApplicationProperty.getStringProperty("security.token.prefix") + " ";

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_NAME);

        if (header == null || !header.startsWith(TOKEN_PREFIX) ||
                req.getRequestURL().toString().contains("/security/")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_NAME);
        if (token != null) {
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

}

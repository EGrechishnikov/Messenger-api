package by.grechishnikov.messenger.security;

import by.grechishnikov.messenger.common.ApplicationProperty;
import by.grechishnikov.messenger.security.filter.JWTAuthenticationFilter;
import by.grechishnikov.messenger.security.filter.JWTAuthorizationFilter;
import by.grechishnikov.messenger.security.service.TokenService;
import by.grechishnikov.messenger.user.service.UserDetailsServiceImpl;
import by.grechishnikov.messenger.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author - Evgeniy Grechishnikov
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private TokenService tokenService;
    private static final String FRONT_END_SERVER_ADDRESS =
            ApplicationProperty.getStringProperty("front-end.address");

    public WebSecurity(UserDetailsServiceImpl userDetailsService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserService userService,
                       TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/security/**").anonymous()
                .antMatchers("/chat/**").anonymous()
                .antMatchers("/swagger**").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/v2/api-docs").anonymous()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), userService, tokenService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), tokenService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));
        configuration.setAllowedOrigins(Collections.singletonList(FRONT_END_SERVER_ADDRESS));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

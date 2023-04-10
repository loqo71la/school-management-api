package dev.loqo71la.schoolmanagement.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides useful methods to manage JWT.
 */
@Component
public class JwtTokenProvider {

    /**
     * Stores the constant pattern to match a Bearer token.
     */
    private static final Pattern BEARER_TOKEN = Pattern.compile("^[Bb]earer (.+)$");

    /**
     * Stores the constant 'username'.
     */
    private static final String USERNAME = "username";

    /**
     * Stores the value of 'spring.jwt.secret' from global properties.
     */
    @Value("${spring.jwt.secret}")
    private String secret;

    /**
     * Gets an {@link Authentication} instance with basic data from http request.
     *
     * @param request incoming HTTP request object to extract the authentication header.
     * @return the authentication instance.
     */
    public Optional<Authentication> getAuthenticationFromRequest(HttpServletRequest request) {
        return getJwtFromHeader(request.getHeader("Authorization"))
                .map(this::getUsernameFromJwt)
                .map(username -> new UsernamePasswordAuthenticationToken(username, null, List.of()));
    }

    /**
     * Gets the username from authentication header.
     *
     * @param header authentication header.
     * @return the username.
     */
    public Optional<String> getUsernameFromHeader(String header) {
        return getJwtFromHeader(header).map(this::getUsernameFromJwt);
    }

    /**
     * Gets the username from JWT.
     *
     * @param jwt JWT received from http request.
     * @return the username.
     */
    private String getUsernameFromJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecret())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .get(USERNAME, String.class);
    }

    /**
     * Gets the JWT from authentication header.
     *
     * @param header authentication header.
     * @return the JWT.
     */
    private Optional<String> getJwtFromHeader(String header) {
        return Optional.ofNullable(header)
                .map(BEARER_TOKEN::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1));
    }

    /**
     * Gets a {@link Key} instance from decoded secret using the HMAC-SHA algorithm.
     *
     * @return the secret key.
     */
    private Key getSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
    }
}

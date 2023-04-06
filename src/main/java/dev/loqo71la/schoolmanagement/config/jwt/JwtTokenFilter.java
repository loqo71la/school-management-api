package dev.loqo71la.schoolmanagement.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements a filter to validate JWT once per request.
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    /**
     * Stores a {@link JwtTokenFilter} instance.
     */
    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * Validates the JWT from received the header, if it is valid extract the username and set an {@link Authentication} instance.
     *
     * @param request     incoming HTTP request object to extract the authentication header.
     * @param response    outgoing HTTP response object
     * @param filterChain filter chain for processing the request
     * @throws ServletException If any servlet-related exception occurs during filtering.
     * @throws IOException      If any Input/Output-related exception occurs during filtering.
     */
    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        tokenProvider.getAuthenticationFromRequest(request)
                .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));
        filterChain.doFilter(request, response);
    }
}

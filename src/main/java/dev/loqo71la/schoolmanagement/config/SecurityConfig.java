package dev.loqo71la.schoolmanagement.config;

import dev.loqo71la.schoolmanagement.config.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Provides a basic configuration to define the endpoints and the types of http methods available.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Stores a {@link JwtTokenFilter} instance.
     */
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    /**
     * Creates a CORS configuration to allow or restrict access from external origins.
     *
     * @param http the {@link HttpSecurity} to modify.
     * @throws Exception when config was not created correctly.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .antMatchers("/api/clazz/**").permitAll()
                .antMatchers("/api/student/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Enables CORS configuration in the Spring Security application. It calls the cors() method on the
     * HttpSecurity object to enable CORS configuration, and specifies the CORS configuration.
     *
     * @return the CORS configuration.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of("http://localhost:5173",
                        "https://school-management.loqo71la.dev",
                        "https://www.school-management.loqo71la.dev"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}

package bg.productions.security;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    /* The JwtAuthenticationFilter intercepts requests,
    verifies JWTs, and sets authentication for requests with valid tokens. */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers(HttpMethod.GET, "/productions/**", "/swagger-ui/**", "swagger-ui.html", "/v3/api-docs/**").permitAll()
                                        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /* The authentication is entirely handled by the JwtAuthenticationFilter,
     which extracts and validates a JWT from the request header without needing a separate AuthenticationProvider.
     The noopAuthenticationProvider serves as a placeholder and fulfills Spring Security’s requirement
     for an AuthenticationProvider to be present, even though it doesn’t actually perform authentication itself.
     Spring Security expects at least one AuthenticationProvider, omitting it would cause configuration issues
     if Spring Security doesn’t find any valid provider in the application context. */
    @Bean
    public AuthenticationProvider noopAuthenticationProvider() {
        return new AuthenticationProvider() {
            /* Returning null tells Spring Security that this provider has not authenticated the request and to try
            the next provider in the chain (if any). In this setup, noopAuthenticationProvider is the only provider,
            so no actual authentication occurs through this provider. */
            @Override
            public Authentication authenticate(Authentication authentication)
                    throws AuthenticationException {
                return null;
            }

            /* Returns false, meaning it doesn’t support any authentication types.
            This effectively disables it from participating in any authentication process. */
            @Override
            public boolean supports(Class<?> authentication) {
                return false;
            }
        };
    }
}

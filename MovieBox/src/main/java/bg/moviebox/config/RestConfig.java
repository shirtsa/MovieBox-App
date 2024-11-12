package bg.moviebox.config;

import bg.moviebox.service.JwtService;
import bg.moviebox.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class RestConfig {

    /* This is a general-purpose RestClient bean for making HTTP requests.
    It’s a basic client without any special configuration, intended for
    simple use cases where you don’t need a base URL or custom headers.
    Can be used for various external calls that don’t require specific configurations. */
    @Bean("genericRestClient")
    public RestClient genericRestClient() {
        return RestClient.create();
    }

    /* Default Header: The default Content-Type is set to application/json,
    ensuring that requests sent with this client are JSON-formatted, which is standard for REST APIs.
    Request Interceptor: This client also uses a ClientHttpRequestInterceptor bean (requestInterceptor)
    that modifies outgoing requests. This interceptor can attach an authorization token in the Authorization header,
    allowing requests to be authenticated via a Bearer token (described in the requestInterceptor bean configuration). */
    @Bean("productionsRestClient")
    public RestClient productionsRestClient(ProductionsApiConfig productionsApiConfig,
                                            ClientHttpRequestInterceptor requestInterceptor) {
        return RestClient
                .builder()
                .baseUrl(productionsApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(requestInterceptor)
                .build();
    }

    /*Interceptor for when we implement JwtService and starts releasing bearerTokens that
    we can read in the Rest API - Productions and will turn the bearerTokens into Users.
    The token is sent with the help of the Interceptor that's implement inside the RestClient
    who makes the request to the productions can be intercepted, and we can add
    different info into the request. Basically we modify the request before its sent to the recipient*/
    @Bean
    public ClientHttpRequestInterceptor requestInterceptor(UserService userService, JwtService jwtService) {
        return (r, b, e) -> {
            //r - request
            //b - body
            //e - execution
            // put the logged user details into bearer token
            userService
                    //Getting the current user that's been populated in the security context
                    // if the user exist the bearerToken is generated with the help of all the claims
                    .getCurrentUser()
                    .ifPresent(mud -> {
                        String bearerToken = jwtService.generateToken(
                                mud.getUuid().toString(),//
                                Map.of( // claims
                                        "roles",
                                        mud.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                                        "user",
                                        mud.getUuid().toString()
                                )
                        );
                        r.getHeaders().setBearerAuth(bearerToken);
                    });
            return e.execute(r, b);
        };
    }
}

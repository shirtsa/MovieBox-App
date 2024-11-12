package bg.productions.security;

import bg.productions.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /* The JwtAuthenticationFilter intercepts each HTTP request and performs the following:
     1.Checks for an Authorization header with a "Bearer" JWT.
     2.Validates the presence and format of the JWT.
     3.Uses JwtService to parse the JWT, extracting user information.
     4.Sets the extracted user details as the authenticated user in Spring Security’s context.
     5.Continues processing, allowing Spring Security and other filters to handle the authenticated request. */

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // doFilterInternal method is executed for each HTTP request.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // The filter retrieves the Authorization header from the request.
        final String authHeader = request.getHeader("Authorization");

        //Authorization: Bearer <token>
        if (authHeader == null ||
                authHeader.isBlank() ||
                !authHeader.startsWith("Bearer ")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // The substring(7) method removes the "Bearer " prefix to isolate the JWT.
        String jwtToken = authHeader.substring(7);

        UserDetails userDetails = jwtService.extractUserInformation(jwtToken);

        /* The SecurityContextHolder holds this authentication information,
        making it accessible throughout the request lifecycle. */
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContext context = SecurityContextHolder.getContext();

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            "",
                            userDetails.getAuthorities()
                    );
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);
        }

        /* After setting the authentication in the security context,
        the filter allows the request to continue through the remaining filters and reach the endpoint. */
        filterChain.doFilter(request, response);
    }
}

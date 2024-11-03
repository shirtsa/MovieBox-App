package bg.moviebox.web;

import bg.moviebox.service.JwtService;
import bg.moviebox.service.impl.MovieBoxUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    private final MovieBoxUserDetailsService movieBoxUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserLoginController(MovieBoxUserDetailsService movieBoxUserDetailsService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.movieBoxUserDetailsService = movieBoxUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestParam String email, @RequestParam String password) throws Exception {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect email or password", e);
        }

        final UserDetails userDetails = movieBoxUserDetailsService.loadUserByUsername(email);
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return jwtService.generateToken(userDetails.getUsername(), claims);
    }

    @PostMapping("/login-error")
    public String loginError(RedirectAttributes redirectAttributes){
        boolean wrongCredentials = true;
        redirectAttributes.addFlashAttribute("wrongCredentials", wrongCredentials);
        return "redirect:/users/login";
    }
}

package bg.moviebox.web;

import bg.moviebox.model.entities.Production;
import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.service.ProductionService;
import bg.moviebox.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {

    private final UserService userService;
    private final ProductionService productionService;

    public PlaylistController(UserService userService, ProductionService productionService) {
        this.userService = userService;
        this.productionService = productionService;
    }

    @DeleteMapping("/{id}")
    public String removeFromPlaylist(@PathVariable Long id, Authentication authentication) {
        MovieBoxUserDetails movieBoxUserDetails = (MovieBoxUserDetails) authentication.getPrincipal();
        productionService.removeFromPlaylist(id, movieBoxUserDetails);
        return "redirect:/playlist";
    }

    @GetMapping
    public String viewPlaylist(Model model, Authentication authentication) {
        MovieBoxUserDetails movieBoxUserDetails = (MovieBoxUserDetails) authentication.getPrincipal();
        Set<Production> playlist = userService.getUserPlaylist(movieBoxUserDetails);
        model.addAttribute("myPlaylist", playlist);
        return "playlist";
    }
}

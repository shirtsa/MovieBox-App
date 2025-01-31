package bg.moviebox.web;

import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.service.CelebrityService;
import bg.moviebox.service.NewsService;
import bg.moviebox.service.ProductionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductionService productionService;
    private final NewsService newsService;
    private final CelebrityService celebrityService;

    public HomeController(ProductionService productionService,
                          NewsService newsService,
                          CelebrityService celebrityService) {
        this.productionService = productionService;
        this.newsService = newsService;
        this.celebrityService = celebrityService;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails instanceof MovieBoxUserDetails movieBoxUserDetails) {
            model.addAttribute("welcomeMessage", movieBoxUserDetails.getFullName());
        } else {
            model.addAttribute("welcomeMessage", "Anonymous");
        }
        model.addAttribute("comingSoon", newsService.getNewsWithNewsTypeComingSoon());
        return "index";
    }

    @GetMapping("/overview")
    public String getMovies(Model model) {
        model.addAttribute("allMovieProductions", productionService.getAllMovieProductions());
        model.addAttribute("allTvProductions", productionService.getAllTvProductions());
        model.addAttribute("allNews", newsService.getAllNewsSummary());
        model.addAttribute("allCelebrities", celebrityService.getAllCelebritySummary());
        return "overview";
    }
}

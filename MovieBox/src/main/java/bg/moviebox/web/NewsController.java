package bg.moviebox.web;

import bg.moviebox.model.dtos.AddNewsDTO;
import bg.moviebox.model.dtos.NewsDetailsDTO;
import bg.moviebox.model.enums.Genre;
import bg.moviebox.model.enums.NewsType;
import bg.moviebox.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @ModelAttribute("newsType")
    public NewsType[] newsTypes() {
        return NewsType.values();
    }

    @GetMapping("/add")
    public String newNews(Model model) {

        if (!model.containsAttribute("addNewsDTO")) {
            model.addAttribute("addNewsDTO", AddNewsDTO.empty());
        }
        model.addAttribute("genres", Genre.values());

        return "/news-add";
    }

    @PostMapping("/add")
    public String addOrUpdateNews(@Valid AddNewsDTO addNewsDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addNewsDTO", addNewsDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addNewsDTO", bindingResult);
            return "redirect:/news/add";
        }

        newsService.createOrUpdateNews(addNewsDTO);
        if (addNewsDTO.id() != null) {
            return "redirect:/news/" + addNewsDTO.id(); // Redirect to the updated news details
        }
        return "redirect:/news";
    }

    @GetMapping("/{id}")
    public String newsDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("newsDetails", newsService.getNewsDetails(id));
        return "news-details";
    }

    @DeleteMapping("/{id}")
    public String deleteNews(@PathVariable("id") Long id) {
        newsService.deleteNews(id);
        return "redirect:/news/all";
    }

    @GetMapping("/all")
    public String getAllNews(Model model) {
        model.addAttribute("allNews", newsService.getAllNewsSummary());
        return "news";
    }

    @GetMapping("/edit/{id}")
    public String editNews(@PathVariable("id") Long id, Model model) {
        NewsDetailsDTO newsDetailsDTO = newsService.getNewsDetails(id);
        AddNewsDTO addNewsDTO = new AddNewsDTO(
                newsDetailsDTO.id(),
                newsDetailsDTO.name(),
                newsDetailsDTO.firstImageUrl(),
                newsDetailsDTO.secondImageUrl(),
                newsDetailsDTO.trailerUrl(),
                newsDetailsDTO.description(),
                newsDetailsDTO.newsType().name()
        );

        model.addAttribute("addNewsDTO", addNewsDTO);
        return "/news-add";
    }

//    @GetMapping("/coming-soon")
//    public String getFirstNews(Model model) {
//            News latestNews = newsService.getFirstNews();
//            model.addAttribute("latestNews", latestNews);
//
//        return "/index";
//    }

}

package bg.moviebox.web;

import bg.moviebox.model.dtos.AddCelebrityDTO;
import bg.moviebox.model.dtos.CelebrityDetailsDTO;
import bg.moviebox.service.CelebrityService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/celebrities")
public class CelebrityController {

    private final CelebrityService celebrityService;

    public CelebrityController(CelebrityService celebrityService) {
        this.celebrityService = celebrityService;
    }

    @GetMapping("/add")
    public String newCelebrity(Model model) {
        if (!model.containsAttribute("addCelebrityDTO")) {
            model.addAttribute("addCelebrityDTO", AddCelebrityDTO.empty());
        }
        return "/celebrity-add";
    }

    @PostMapping("/add")
    public String addOrUpdateCelebrity(@Valid AddCelebrityDTO addCelebrityDTO,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCelebrityDTO", addCelebrityDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCelebrityDTO", bindingResult);
            return "redirect:/celebrities/add";
        }

        celebrityService.createOrUpdateCelebrity(addCelebrityDTO);
        if (addCelebrityDTO.id() != null) {
            return "redirect:/celebrities/" + addCelebrityDTO.id();
        }
        return "redirect:/celebrities/all";
    }

    @GetMapping("/all")
    public String getAllCelebrities(Model model) {
        model.addAttribute("allCelebrities", celebrityService.getAllCelebritySummary());
        return "/celebrities";
    }

    @GetMapping("/{id}")
    public String celebrityDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("celebrityDetails", celebrityService.getCelebrityDetails(id));
        return "celebrities-details";
    }

    @DeleteMapping("/{id}")
    public String deleteCelebrity(@PathVariable("id") Long id) {
        celebrityService.deleteCelebrity(id);
        return "redirect:/celebrities/all";
    }

    @GetMapping("/edit/{id}")
    public String editCelebrity(@PathVariable("id") Long id, Model model) {
        CelebrityDetailsDTO celebrityDetailsDTO = celebrityService.getCelebrityDetails(id);
        AddCelebrityDTO addCelebrityDTO = new AddCelebrityDTO(
                celebrityDetailsDTO.id(),
                celebrityDetailsDTO.name(),
                celebrityDetailsDTO.imageUrl(),
                celebrityDetailsDTO.biography()
        );

        model.addAttribute("addCelebrityDTO", addCelebrityDTO);
        return "/celebrity-add";
    }
}

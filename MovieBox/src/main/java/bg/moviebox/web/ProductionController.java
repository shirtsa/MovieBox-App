package bg.moviebox.web;

import bg.moviebox.model.dtos.AddProductionDTO;
import bg.moviebox.model.dtos.ProductionDetailsDTO;
import bg.moviebox.model.enums.Genre;
import bg.moviebox.model.enums.ProductionType;
import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.service.ProductionService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/productions")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @ModelAttribute("productionTypes")
    public ProductionType[] productionTypes() {
        return ProductionType.values();
    }

    @ModelAttribute("genreTypes")
    public Genre[] genreTypes() {
        return Genre.values();
    }

    @GetMapping("/add")
    public String newProduction(Model model) {

        if (!model.containsAttribute("addProductionDTO")) {
            model.addAttribute("addProductionDTO", AddProductionDTO.empty());
        }
        model.addAttribute("genres", Genre.values());
        return "/production-add";
    }

    // all errors from input info comes in the bindingResult
    @PostMapping("/add")
    public String addOrUpdateProduction(@Valid AddProductionDTO addProductionDTO,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {

        // addFlashAttribute exist for short period of time to remembers the info
        // from the current session so can be used in the next session
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addProductionDTO", addProductionDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductionDTO", bindingResult);
            return "redirect:/productions/add";
        }

        productionService.createOrUpdateProduction(addProductionDTO);
        if (addProductionDTO.id() != null) {
            return "redirect:/productions/" + addProductionDTO.id();
        }
        return "redirect:/productions/all";
    }

    @GetMapping("/edit/{id}")
    public String editProduction(@PathVariable("id") Long id, Model model) {
        ProductionDetailsDTO productionDetailsDTO = productionService.getProductionDetails(id);
        AddProductionDTO addProductionDTO = new AddProductionDTO(
                productionDetailsDTO.id(),
                productionDetailsDTO.name(),
                productionDetailsDTO.imageUrl(),
                productionDetailsDTO.videoUrl(),
                productionDetailsDTO.year(),
                productionDetailsDTO.length(),
                productionDetailsDTO.rating(),
                productionDetailsDTO.rentPrice(),
                productionDetailsDTO.description(),
                productionDetailsDTO.productionType(),
                productionDetailsDTO.genre()
        );
        model.addAttribute("addProductionDTO", addProductionDTO);
        return "/production-add";
    }

    @DeleteMapping("/{id}")
    public String deleteProduction(@PathVariable("id") Long id) {
        productionService.deleteProduction(id);
        return "redirect:/productions/all";
    }

    @GetMapping("/{id}")
    public String productionDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("productionDetails", productionService.getProductionDetails(id));
        return "details";
    }

    @PostMapping("/{id}")
    public String addProductionToPlaylist(@PathVariable("id") Long id, Principal principal) {
        MovieBoxUserDetails userDetails =
                (MovieBoxUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        productionService.addToPlaylist(id, userDetails);

        return "redirect:/playlist";
    }

    @GetMapping("/all")
    public String getAllProductions(Model model) {
        model.addAttribute("allProductions", productionService.getAllProductionsSummary());
        return "productions";
    }

    @GetMapping("/movies")
    public String movies(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (!(userDetails instanceof MovieBoxUserDetails)) {
            return "redirect:/login";
        }

        model.addAttribute("allMovieProductions", productionService.getAllMovieProductions());
        return "/movies";
    }

    @GetMapping("/tv")
    public String tv(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (!(userDetails instanceof MovieBoxUserDetails)) {
            return "redirect:/login";
        }

        model.addAttribute("allTvProductions", productionService.getAllTvProductions());
        return "/tv";
    }
}

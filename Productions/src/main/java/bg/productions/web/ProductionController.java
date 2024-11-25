package bg.productions.web;

import bg.productions.model.dtos.AddProductionDTO;
import bg.productions.model.dtos.ProductionDTO;
import bg.productions.model.enums.ProductionType;
import bg.productions.service.MonitoringService;
import bg.productions.service.ProductionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/productions")
@Tag(
        name = "Productions",
        description = "The controller responsible for production management."
)
public class ProductionController {

    private final ProductionService productionService;
    private final MonitoringService monitoringService;

    public ProductionController(ProductionService productionService, MonitoringService monitoringService) {
        this.productionService = productionService;
        this.monitoringService = monitoringService;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The production details",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProductionDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404", description = "If the production was not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            }
                    )
            }
    )

    @PostMapping
    public ResponseEntity<ProductionDTO> createProduction(@RequestBody AddProductionDTO addProductionDTO) {
        ProductionDTO productionDTO = productionService.createProduction(addProductionDTO);
        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(productionDTO.id())
                                .toUri()
                ).body(productionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductionDTO> updateProduction(@PathVariable("id") Long id,
            @RequestBody AddProductionDTO addProductionDTO) {
        ProductionDTO updatedProduction = productionService.updateProduction(id, addProductionDTO);
        return ResponseEntity.ok(updatedProduction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionDTO> getById(@PathVariable("id") Long id) {
        monitoringService.increaseProductionSearches();
        return ResponseEntity.ok(productionService.getProductionById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductionDTO>> getAllProductions() {
        monitoringService.increaseProductionSearches();
        return ResponseEntity.ok(productionService.getAllProductions());
    }

    @GetMapping("/movies")
    public ResponseEntity<List<ProductionDTO>> getAllMovieProductions() {
        monitoringService.increaseProductionSearches();
        return ResponseEntity.ok(productionService.getProductionsByProductionType(ProductionType.MOVIE));
    }

    @GetMapping("/tv")
    public ResponseEntity<List<ProductionDTO>> getAllTvProductions() {
        monitoringService.increaseProductionSearches();
        return ResponseEntity.ok(productionService.getProductionsByProductionType(ProductionType.TV));
    }

    @Operation(
            security = @SecurityRequirement(
                    name = "bearer-token"
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductionDTO> deleteById(@PathVariable("id") Long id) {
        productionService.deleteProduction(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}

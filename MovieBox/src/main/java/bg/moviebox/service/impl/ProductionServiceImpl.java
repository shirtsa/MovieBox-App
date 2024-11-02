package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.AddProductionDTO;
import bg.moviebox.model.dtos.ProductionDetailsDTO;
import bg.moviebox.model.dtos.ProductionSummaryDTO;
import bg.moviebox.model.entities.Production;
import bg.moviebox.model.entities.User;
import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.repository.ProductionRepository;
import bg.moviebox.repository.UserRepository;
import bg.moviebox.service.ExchangeRateService;
import bg.moviebox.service.ProductionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ProductionServiceImpl implements ProductionService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductionServiceImpl.class);
    private final RestClient productionRestClient;
    private final ExchangeRateService exchangeRateService;
    private final UserRepository userRepository;
    private final ProductionRepository productionRepository;


    public ProductionServiceImpl(@Qualifier("productionsRestClient") RestClient productionRestClient,
                                 ExchangeRateService exchangeRateService, UserRepository userRepository, ProductionRepository productionRepository) {
        this.productionRestClient = productionRestClient;
        this.exchangeRateService = exchangeRateService;
        this.userRepository = userRepository;
        this.productionRepository = productionRepository;
    }

    @Override
    public void createProduction(AddProductionDTO addProductionDTO) {
        LOGGER.info("Creating new production...");
        productionRestClient
                .post()
                .uri("/productions") //.uri("http://localhost:8081/productions")
                .body(addProductionDTO)
                .retrieve();
    }

    @Override
    public void deleteProduction(Long productionId) {
        LOGGER.info("Attempting to delete production with ID: {}", productionId);
        productionRestClient
                .delete()
                .uri("/productions/" + productionId)
                .retrieve();
        LOGGER.info("Successfully deleted production with ID: {}", productionId);
    }

    @Override
    public ProductionDetailsDTO getProductionDetails(Long id) {
        return productionRestClient
                .get()
                .uri("/productions/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ProductionDetailsDTO.class);
    }

    @Override
    public void addToPlaylist(Long productionId, MovieBoxUserDetails movieBoxUserDetails) {
        User user = userRepository.findById(movieBoxUserDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductionDetailsDTO productionDetailsDTO = productionRestClient
                .get()
                .uri("/productions/{id}", productionId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ProductionDetailsDTO.class);

        assert productionDetailsDTO != null;
        Production production = productionRepository.save(map(productionDetailsDTO));

        user.getPlaylist().add(production);
        userRepository.save(user);
    }

    @Override
    public List<ProductionSummaryDTO> getAllProductionsSummary() {
        LOGGER.info("Get all productions...");
        return productionRestClient
                .get()
                .uri("/productions")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public List<ProductionDetailsDTO> getAllMovieProductions() {
        return productionRestClient
                .get()
                .uri("/productions/movies")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public List<ProductionDetailsDTO> getAllTvProductions() {
        return productionRestClient
                .get()
                .uri("/productions/tv")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().body(new ParameterizedTypeReference<>(){});
    }

    //Mapped Production entity to ProductionDetails DTO
    private ProductionDetailsDTO toProductionDetailsDTO(Production production) {
        return new ProductionDetailsDTO(
                production.getId(),
                production.getName(),
                production.getImageUrl(),
                production.getVideoUrl(),
                production.getYear(),
                production.getLength(),
                production.getRating(),
                production.getRentPrice(),
                production.getDescription(),
                production.getProductionType(),
                production.getGenre(),
                exchangeRateService.allSupportedCurrencies());
    }

    //Mapped ProductionDetailsDTO to production entity
    private static Production map(ProductionDetailsDTO productionDetailsDTO) {
        return new Production()
                .setName(productionDetailsDTO.name())
                .setImageUrl(productionDetailsDTO.imageUrl())
                .setGenre(productionDetailsDTO.genre())
                .setLength(productionDetailsDTO.length())
                .setRating(productionDetailsDTO.rating())
                .setRentPrice(productionDetailsDTO.rentPrice())
                .setVideoUrl(productionDetailsDTO.videoUrl())
                .setYear(productionDetailsDTO.year())
                .setDescription(productionDetailsDTO.description())
                .setProductionType(productionDetailsDTO.productionType());
    }
}

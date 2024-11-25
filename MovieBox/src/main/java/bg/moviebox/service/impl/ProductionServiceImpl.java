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
import bg.moviebox.service.exception.ApiObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ProductionServiceImpl implements ProductionService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductionServiceImpl.class);
    private final RestClient productionRestClient;
    private final ExchangeRateService exchangeRateService;
    private final UserRepository userRepository;
    private final ProductionRepository productionRepository;

    public ProductionServiceImpl(@Qualifier("productionsRestClient")
                                 RestClient productionRestClient,
                                 ExchangeRateService exchangeRateService,
                                 UserRepository userRepository,
                                 ProductionRepository productionRepository) {
        this.productionRestClient = productionRestClient;
        this.exchangeRateService = exchangeRateService;
        this.userRepository = userRepository;
        this.productionRepository = productionRepository;
    }

    @Override
    public void createOrUpdateProduction(AddProductionDTO addProductionDTO) {
        if (addProductionDTO.id() != null) {
            // Fetch the existing production
            Production existingProduction = productionRestClient
                    .get()
                    .uri("productions/{id}", addProductionDTO.id())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(Production.class);

            // Update fields of the existing production
            existingProduction.setId(addProductionDTO.id());
            existingProduction.setName(addProductionDTO.name());
            existingProduction.setImageUrl(addProductionDTO.imageUrl());
            existingProduction.setVideoUrl(addProductionDTO.videoUrl());
            existingProduction.setYear(addProductionDTO.year());
            existingProduction.setLength(addProductionDTO.length());
            existingProduction.setRating(addProductionDTO.rating());
            existingProduction.setRentPrice(addProductionDTO.rentPrice());
            existingProduction.setDescription(addProductionDTO.description());
            existingProduction.setProductionType(addProductionDTO.productionType());
            existingProduction.setGenre(addProductionDTO.genre());

            // Make an HTTP PUT request to update the production
            productionRestClient
                    .put()
                    .uri("/productions/{id}", existingProduction.getId())
                    .body(existingProduction)
                    .retrieve();
        } else {
            LOGGER.info("Creating new production...");
            productionRestClient
                    .post()
                    .uri("/productions") //.uri("http://localhost:8081/productions")
                    .body(addProductionDTO)
                    .retrieve();
        }
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
        ProductionDetailsDTO fetchedDetails = productionRestClient
                .get()
                .uri("/productions/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ProductionDetailsDTO.class);

        return new ProductionDetailsDTO(
                fetchedDetails.id(),
                fetchedDetails.name(),
                fetchedDetails.imageUrl(),
                fetchedDetails.videoUrl(),
                fetchedDetails.year(),
                fetchedDetails.length(),
                fetchedDetails.rating(),
                fetchedDetails.rentPrice(),
                fetchedDetails.description(),
                fetchedDetails.productionType(),
                fetchedDetails.genre(),
                exchangeRateService.allSupportedCurrencies());
    }

    @Override
    public void addToPlaylist(Long productionId, MovieBoxUserDetails movieBoxUserDetails) {
        User user = userRepository.findById(movieBoxUserDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Retrieve the production from the external service
        ProductionDetailsDTO productionDetailsDTO = productionRestClient
                .get()
                .uri("/productions/{id}", productionId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ProductionDetailsDTO.class);

        // If the production does not exist in the external service throw exception
        if (productionDetailsDTO == null) {
            throw new ApiObjectNotFoundException(
                    "Production with id: ", productionId + " not found in external service");
        }

        // Map ProductionDetailsDTO to Production entity and save it
        Production newProduction = map(productionDetailsDTO);
        // Set the external ID to match the production ID from the external service
        newProduction.setExternalId(productionId);

        // Check if the production is already in the user's playlist
        boolean isAlreadyInPlaylist = user
                .getPlaylist()
                .stream()
                .anyMatch(existingProduction -> existingProduction.getExternalId()
                        .equals(productionId));

        // If it's already in the playlist, do nothing
        if (isAlreadyInPlaylist) {
            return;
        }

        // Add the production to the user's playlist and save it
        user.getPlaylist().add(newProduction);
        productionRepository.save(newProduction);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeFromPlaylist(Long externalId, MovieBoxUserDetails movieBoxUserDetails) {
        User user = userRepository.findById(movieBoxUserDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Production production = productionRepository.findProductionByExternalId(externalId);
        if (production == null) {
            throw new ApiObjectNotFoundException("Production with id: ", externalId + " not found!");
        }

        // Remove from the user's playlist if it exists
        if (user.getPlaylist().contains(production)) {
            user.getPlaylist().remove(production);
            productionRepository.deleteById(production.getId());
            userRepository.save(user); // Save the user to update the playlist association
        }
    }

    @Override
    public List<ProductionSummaryDTO> getAllProductionsSummary() {
        LOGGER.info("Get all productions...");
        return productionRestClient
                .get()
                .uri("/productions")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public List<ProductionDetailsDTO> getAllMovieProductions() {
        return productionRestClient
                .get()
                .uri("/productions/movies")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public List<ProductionDetailsDTO> getAllTvProductions() {
        return productionRestClient
                .get()
                .uri("/productions/tv")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
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

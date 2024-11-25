package bg.moviebox.service;

import bg.moviebox.model.dtos.AddProductionDTO;
import bg.moviebox.model.dtos.ProductionDetailsDTO;
import bg.moviebox.model.dtos.ProductionSummaryDTO;
import bg.moviebox.model.user.MovieBoxUserDetails;

import java.util.List;

public interface ProductionService {

    void createOrUpdateProduction(AddProductionDTO addProductionDTO); //long

    void deleteProduction(Long productionId);

    ProductionDetailsDTO getProductionDetails(Long id);

    List<ProductionSummaryDTO> getAllProductionsSummary();

    List<ProductionDetailsDTO> getAllMovieProductions();

    List<ProductionDetailsDTO> getAllTvProductions();

    void addToPlaylist(Long productionId, MovieBoxUserDetails movieBoxUserDetails);

    void removeFromPlaylist(Long externalId, MovieBoxUserDetails movieBoxUserDetails);

}

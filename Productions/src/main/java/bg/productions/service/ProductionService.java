package bg.productions.service;

import bg.productions.model.dtos.AddProductionDTO;
import bg.productions.model.dtos.ProductionDTO;
import bg.productions.model.enums.ProductionType;

import java.util.List;

public interface ProductionService {

    ProductionDTO createProduction(AddProductionDTO addProductionDTO);

    ProductionDTO updateProduction(Long id, AddProductionDTO addProductionDTO);

    void deleteProduction(Long productionId);

    ProductionDTO getProductionById(Long productionId);

    List<ProductionDTO> getAllProductions();

    List<ProductionDTO> getProductionsByProductionType(ProductionType productionType);
}

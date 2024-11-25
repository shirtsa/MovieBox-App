package bg.productions.service.impl;

import bg.productions.model.dtos.AddProductionDTO;
import bg.productions.model.dtos.ProductionDTO;
import bg.productions.model.entities.Production;
import bg.productions.model.enums.ProductionType;
import bg.productions.repository.ProductionRepository;
import bg.productions.service.ProductionService;
import bg.productions.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionServiceImpl implements ProductionService {

    private final ProductionRepository productionRepository;

    public ProductionServiceImpl(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @Override
    public ProductionDTO createProduction(AddProductionDTO addProductionDTO) {
        Production production = productionRepository.save(map(addProductionDTO));
        return map(production);
    }

    @Override
    public ProductionDTO updateProduction(Long id, AddProductionDTO addProductionDTO) {
        // Fetch the existing production
        Production existingProduction = productionRepository.findById(id)
                .orElseThrow(ObjectNotFoundException::new);

        // Update fields
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

        // Save updated production
        productionRepository.save(existingProduction);
        // Map Production to ProductionDTO
        return map(existingProduction);
    }

    @Override
    public ProductionDTO getProductionById(Long productionId) {
        return productionRepository
                .findById(productionId)
                .map(ProductionServiceImpl::map)
                .orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public List<ProductionDTO> getAllProductions() {
        return productionRepository
                .findAll()
                .stream()
                .map(ProductionServiceImpl::map)
                .toList();
    }

    @Override
    public List<ProductionDTO> getProductionsByProductionType(ProductionType productionType) {
        return productionRepository
                .findAll()
                .stream()
                .filter(production -> production.getProductionType().equals(productionType))
                .map(ProductionServiceImpl::map)
                .toList();
    }

    @Override
    public void deleteProduction(Long productionId) {
        if (!productionRepository.existsById(productionId)) {
            throw new ObjectNotFoundException();
        }
        productionRepository.deleteById(productionId);
    }

    //Mapped Production to ProductionDTO
    private static ProductionDTO map(Production production) {
        return new ProductionDTO(
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
                production.getGenre());
    }

    //Mapped Production DTO to production entity
    private static Production map(AddProductionDTO addProductionDTO) {
        return new Production()
                .setName(addProductionDTO.name())
                .setImageUrl(addProductionDTO.imageUrl())
                .setGenre(addProductionDTO.genre())
                .setLength(addProductionDTO.length())
                .setRating(addProductionDTO.rating())
                .setRentPrice(addProductionDTO.rentPrice())
                .setVideoUrl(addProductionDTO.videoUrl())
                .setYear(addProductionDTO.year())
                .setDescription(addProductionDTO.description())
                .setProductionType(addProductionDTO.productionType());
    }
}
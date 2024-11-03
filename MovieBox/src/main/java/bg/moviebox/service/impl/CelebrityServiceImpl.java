package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.AddCelebrityDTO;
import bg.moviebox.model.dtos.CelebrityDetailsDTO;
import bg.moviebox.model.dtos.CelebritySummaryDTO;
import bg.moviebox.model.entities.Celebrity;
import bg.moviebox.repository.CelebrityRepository;
import bg.moviebox.service.CelebrityService;
import bg.moviebox.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CelebrityServiceImpl implements CelebrityService {

    private final CelebrityRepository celebrityRepository;

    public CelebrityServiceImpl(CelebrityRepository celebrityRepository) {
        this.celebrityRepository = celebrityRepository;
    }

    @Override
    public void createCelebrity(AddCelebrityDTO addCelebrityDTO) {
        Celebrity celebrity = map(addCelebrityDTO);
        celebrityRepository.save(celebrity);
    }

    @Override
    public void deleteCelebrity(Long celebrityId) {
        celebrityRepository.deleteById(celebrityId);
    }

    @Override
    public CelebrityDetailsDTO getCelebrityDetails(Long id) {
        return this.celebrityRepository
                .findById(id)
                .map(this::toCelebrityDetailsDTO)
                .orElseThrow(() -> new ObjectNotFoundException("Celebrity not found!", id));
    }

    @Override
    public List<CelebritySummaryDTO> getAllCelebritySummary() {
        return celebrityRepository
                .findAll()
                .stream()
                .map(CelebrityServiceImpl::toCelebritySummary)
                .toList();
    }

    private static CelebritySummaryDTO toCelebritySummary(Celebrity celebrity) {
        return new CelebritySummaryDTO(
                celebrity.getId(),
                celebrity.getName(),
                celebrity.getImageUrl(),
                celebrity.getBiography());
    }

    private CelebrityDetailsDTO toCelebrityDetailsDTO(Celebrity celebrity) {
        return new CelebrityDetailsDTO(
                celebrity.getId(),
                celebrity.getName(),
                celebrity.getImageUrl(),
                celebrity.getBiography());
    }

    private static Celebrity map(AddCelebrityDTO addCelebrityDTO) {
        return new Celebrity()
                .setName(addCelebrityDTO.name())
                .setImageUrl(addCelebrityDTO.imageUrl())
                .setBiography(addCelebrityDTO.biography());
    }
}

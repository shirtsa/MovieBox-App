package bg.moviebox.service;

import bg.moviebox.model.dtos.AddCelebrityDTO;
import bg.moviebox.model.dtos.CelebrityDetailsDTO;
import bg.moviebox.model.dtos.CelebritySummaryDTO;

import java.util.List;

public interface CelebrityService {

    void createOrUpdateCelebrity(AddCelebrityDTO addCelebrityDTO);

    void deleteCelebrity(Long celebrityId);

    CelebrityDetailsDTO getCelebrityDetails(Long id);

    List<CelebritySummaryDTO> getAllCelebritySummary();
}

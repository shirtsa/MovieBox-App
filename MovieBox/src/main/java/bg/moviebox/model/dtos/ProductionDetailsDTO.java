package bg.moviebox.model.dtos;

import bg.moviebox.model.enums.Genre;
import bg.moviebox.model.enums.ProductionType;

import java.util.List;

public record ProductionDetailsDTO(
        Long id,
        String name,
        String imageUrl,
        String videoUrl,
        Integer year,
        Integer length,
        Integer rating,
        Integer rentPrice,
        String description,
        ProductionType productionType,
        Genre genre,
        List<String> allCurrencies) {

}

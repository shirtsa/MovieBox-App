package bg.moviebox.model.dtos;

import java.math.BigDecimal;

public record ConversionResultDTO(
        String from,
        String to,
        BigDecimal amount,
        BigDecimal result) {
}

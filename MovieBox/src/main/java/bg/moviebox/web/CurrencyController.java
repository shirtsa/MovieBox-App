package bg.moviebox.web;

import bg.moviebox.model.dtos.ConversionResultDTO;
import bg.moviebox.service.ExchangeRateService;
import bg.moviebox.service.exception.ApiObjectNotFoundException;
import bg.moviebox.web.aop.WarnIfExecutionExceeds;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class CurrencyController {

    private final ExchangeRateService exRateService;

    public CurrencyController(ExchangeRateService exRateService) {
        this.exRateService = exRateService;
    }

    @WarnIfExecutionExceeds(
            threshold = 800
    )
    @GetMapping("/api/convert")
    public ResponseEntity<ConversionResultDTO> convert(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("amount") BigDecimal amount
    ) throws InterruptedException {
        BigDecimal result = exRateService.convert(from, to, amount);
//    Thread.sleep(5000);
        return ResponseEntity.ok(new ConversionResultDTO(
                from,
                to,
                amount,
                result
        ));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ApiObjectNotFoundException.class)
    @ResponseBody
    public NotFoundErrorInfo handleApiObjectNotFoundException(ApiObjectNotFoundException apiObjectNotFoundException) {
        return new NotFoundErrorInfo("NOT FOUND", apiObjectNotFoundException.getId());
    }

    public record NotFoundErrorInfo(
            String code,
            Object id) {
    }
}

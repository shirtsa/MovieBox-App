package bg.moviebox.service.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class YearValidator implements ConstraintValidator<YearConstraint, Integer> {

    @Override
    public void initialize(YearConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        if (year == null) {
            return true;
        }
        int currentYear = Year.now().getValue();
        return year >= 1906 && year <= currentYear;
    }
}
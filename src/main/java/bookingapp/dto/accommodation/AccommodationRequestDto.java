package bookingapp.dto.accommodation;

import bookingapp.dto.address.AddressRequestDto;
import bookingapp.model.accommodation.Accommodation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;

public record AccommodationRequestDto(
        @NotNull
        Accommodation.Type accommodationType,
        @NotNull
        AddressRequestDto address,
        @NotBlank
        String size,
        @NotEmpty
        Set<Long> amenityIds,
        @NotNull
        @Positive
        BigDecimal dailyRate,
        @NotNull
        @Positive
        Integer availability
) {
}

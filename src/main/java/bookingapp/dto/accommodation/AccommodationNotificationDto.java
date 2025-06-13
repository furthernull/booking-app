package bookingapp.dto.accommodation;

public record AccommodationNotificationDto(
        String accommodationTypeName,
        String accommodationSize,
        String amenities,
        String location
) {
}

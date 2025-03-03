package bookingapp.dto.booking;

import bookingapp.dto.accommodation.AccommodationNotificationDto;
import java.time.LocalDate;

public record BookingNotificationDto(
        Long userId,
        String bookingStatus,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        AccommodationNotificationDto accommodation
) {}

package bookingapp.dto.booking;

import bookingapp.model.booking.Booking;

public record BookingFilterParameters(
        Long userId,
        Booking.Status status
) {
}

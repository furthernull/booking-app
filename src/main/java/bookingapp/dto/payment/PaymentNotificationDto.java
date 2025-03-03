package bookingapp.dto.payment;

public record PaymentNotificationDto(
        Long userId,
        Long bookingId,
        String paymentStatus,
        String firstName,
        String lastName
) {
}

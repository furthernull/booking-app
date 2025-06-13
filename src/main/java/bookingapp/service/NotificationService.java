package bookingapp.service;

import bookingapp.dto.accommodation.AccommodationNotificationDto;
import bookingapp.dto.booking.BookingNotificationDto;
import bookingapp.dto.payment.PaymentNotificationDto;
import java.util.List;

public interface NotificationService {
    void sendNotification(BookingNotificationDto bookingNotificationDto);

    void sendNotification(AccommodationNotificationDto accommodationNotificationDto);

    void sendNotification(List<BookingNotificationDto> expiringBookings);

    void sendNotification(PaymentNotificationDto paymentNotificationDto);
}

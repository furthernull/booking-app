package bookingapp.telegram.strategy.booking.impl;

import bookingapp.telegram.NotificationTemplates;
import bookingapp.telegram.strategy.booking.BookingNotificationService;
import bookingapp.telegram.strategy.booking.BookingNotificationType;
import org.springframework.stereotype.Service;

@Service(BookingNotificationType.CANCELLED)
public class CancelBookingNotificationService implements BookingNotificationService {
    @Override
    public String getNotification() {
        return NotificationTemplates.NOTIFICATION_CANCEL_TEMPLATE;
    }
}

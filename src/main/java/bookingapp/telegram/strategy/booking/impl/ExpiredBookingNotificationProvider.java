package bookingapp.telegram.strategy.booking.impl;

import bookingapp.telegram.NotificationTemplates;
import bookingapp.telegram.strategy.booking.BookingNotificationProvider;
import org.springframework.stereotype.Component;

@Component
public class ExpiredBookingNotificationProvider implements BookingNotificationProvider {
    @Override
    public String getKey() {
        return EXPIRED_KEY;
    }

    @Override
    public String getNotification() {
        return NotificationTemplates.NOTIFICATION_EXPIRED_TEMPLATE;
    }
}

package bookingapp.telegram.strategy.booking;

import bookingapp.telegram.strategy.NotificationProvider;

public interface BookingNotificationProvider extends NotificationProvider {
    String PENDING_KEY = "PENDING";
    String CONFIRMED_KEY = "CONFIRMED";
    String CANCELLED_KEY = "CANCELLED";
    String EXPIRED_KEY = "EXPIRED";
}

package bookingapp.telegram.strategy.payment;

import bookingapp.telegram.strategy.NotificationProvider;

public interface PaymentNotificationProvider extends NotificationProvider {
    String PAID_KEY = "PAID";
    String PENDING_KEY = "PENDING";
}

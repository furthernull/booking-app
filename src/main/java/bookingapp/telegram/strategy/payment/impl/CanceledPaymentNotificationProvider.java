package bookingapp.telegram.strategy.payment.impl;

import bookingapp.telegram.NotificationTemplates;
import bookingapp.telegram.strategy.payment.PaymentNotificationProvider;
import org.springframework.stereotype.Component;

@Component
public class CanceledPaymentNotificationProvider implements PaymentNotificationProvider {
    @Override
    public String getKey() {
        return PENDING_KEY;
    }

    @Override
    public String getNotification() {
        return NotificationTemplates.PAYMENT_CANCELED_MESSAGE;
    }
}

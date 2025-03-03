package bookingapp.telegram.strategy.payment.impl;

import bookingapp.telegram.NotificationTemplates;
import bookingapp.telegram.strategy.payment.PaymentNotificationProvider;
import org.springframework.stereotype.Component;

@Component
public class PaidPaymentNotificationProvider implements PaymentNotificationProvider {
    @Override
    public String getKey() {
        return PAID_KEY;
    }

    @Override
    public String getNotification() {
        return NotificationTemplates.PAYMENT_SUCCESSFUL_MESSAGE;
    }
}

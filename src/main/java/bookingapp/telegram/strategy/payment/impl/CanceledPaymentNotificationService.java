package bookingapp.telegram.strategy.payment.impl;

import bookingapp.telegram.NotificationTemplates;
import bookingapp.telegram.strategy.payment.PaymentNotificationService;
import bookingapp.telegram.strategy.payment.PaymentNotificationType;
import org.springframework.stereotype.Service;

@Service(PaymentNotificationType.AWAITING)
public class CanceledPaymentNotificationService implements PaymentNotificationService {
    @Override
    public String getNotification() {
        return NotificationTemplates.PAYMENT_CANCELED_MESSAGE;
    }
}

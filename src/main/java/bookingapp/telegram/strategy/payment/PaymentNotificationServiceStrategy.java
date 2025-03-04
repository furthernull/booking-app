package bookingapp.telegram.strategy.payment;

import bookingapp.exception.NotificationException;
import bookingapp.telegram.strategy.NotificationServiceStrategy;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentNotificationServiceStrategy implements NotificationServiceStrategy {
    private final Map<String, PaymentNotificationService> notificationMap;

    @Override
    public PaymentNotificationService getNotificationService(String notificationType) {
        if (!notificationMap.containsKey(notificationType)) {
            throw new NotificationException(
                    "Can't find PaymentNotificationService by notification type: "
                            + notificationType);
        }
        return notificationMap.get(notificationType);
    }
}

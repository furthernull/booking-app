package bookingapp.telegram.strategy.payment;

import bookingapp.exception.NotificationException;
import bookingapp.telegram.strategy.NotificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentNotificationProviderManager implements NotificationProviderManager {
    private final List<PaymentNotificationProvider> notificationProviders;

    @Override
    public PaymentNotificationProvider getNotificationProvider(String key) {
        return notificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NotificationException(
                        "Can't find PaymentNotificationProvider for key: " + key));
    }
}

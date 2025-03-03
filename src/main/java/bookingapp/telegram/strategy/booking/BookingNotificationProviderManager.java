package bookingapp.telegram.strategy.booking;

import bookingapp.exception.NotificationException;
import bookingapp.telegram.strategy.NotificationProvider;
import bookingapp.telegram.strategy.NotificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookingNotificationProviderManager implements NotificationProviderManager {
    private final List<BookingNotificationProvider> notificationProviders;

    @Override
    public NotificationProvider getNotificationProvider(String key) {
        return notificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NotificationException(
                        "Can't find BookingNotificationProvider for key: " + key));
    }
}

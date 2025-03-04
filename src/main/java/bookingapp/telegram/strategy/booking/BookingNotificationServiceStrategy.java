package bookingapp.telegram.strategy.booking;

import bookingapp.exception.NotificationException;
import bookingapp.telegram.strategy.NotificationService;
import bookingapp.telegram.strategy.NotificationServiceStrategy;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookingNotificationServiceStrategy implements NotificationServiceStrategy {
    private final Map<String, BookingNotificationService> notificationMap;

    @Override
    public NotificationService getNotificationService(String notificationType) {
        if (!notificationMap.containsKey(notificationType)) {
            throw new NotificationException(
                    "Can't find BookingNotificationService by notification type: "
                            + notificationType);
        }
        return notificationMap.get(notificationType);
    }
}

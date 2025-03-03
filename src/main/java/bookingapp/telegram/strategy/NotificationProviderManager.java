package bookingapp.telegram.strategy;

public interface NotificationProviderManager {
    NotificationProvider getNotificationProvider(String key);
}

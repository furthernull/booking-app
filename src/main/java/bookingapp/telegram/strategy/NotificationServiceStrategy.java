package bookingapp.telegram.strategy;

public interface NotificationServiceStrategy {
    NotificationService getNotificationService(String notificationType);
}

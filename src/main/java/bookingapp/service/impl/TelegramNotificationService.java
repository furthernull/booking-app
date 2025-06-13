package bookingapp.service.impl;

import bookingapp.dto.accommodation.AccommodationNotificationDto;
import bookingapp.dto.booking.BookingNotificationDto;
import bookingapp.dto.payment.PaymentNotificationDto;
import bookingapp.model.telegram.TelegramChat;
import bookingapp.repository.telegram.TelegramRepository;
import bookingapp.service.NotificationService;
import bookingapp.telegram.NotificationTemplates;
import bookingapp.telegram.TelegramBot;
import bookingapp.telegram.strategy.booking.BookingNotificationServiceStrategy;
import bookingapp.telegram.strategy.payment.PaymentNotificationServiceStrategy;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TelegramNotificationService implements NotificationService {
    private final BookingNotificationServiceStrategy bookingNotificationServiceStrategy;
    private final PaymentNotificationServiceStrategy paymentNotificationServiceStrategy;
    private final TelegramBot telegramBot;
    private final TelegramRepository telegramRepository;

    @Async
    @Transactional
    @Override
    public void sendNotification(BookingNotificationDto bookingNotificationDto) {
        getChat(bookingNotificationDto.userId()).filter(TelegramChat::isSubscribed)
                .ifPresent(chat ->
                        telegramBot.sendMessage(
                                chat.getChatId(),
                                prepareNotification(chat, bookingNotificationDto
                                )));
    }

    @Async
    @Transactional
    @Override
    public void sendNotification(AccommodationNotificationDto accommodationNotificationDto) {
        String notification = prepareNotification(accommodationNotificationDto);
        telegramRepository.findAllByIsSubscribedIsTrue()
                .forEach(c -> telegramBot.sendMessage(c.getChatId(), notification));
    }

    @Async
    @Transactional
    @Override
    public void sendNotification(List<BookingNotificationDto> expiringBookings) {
        if (expiringBookings.isEmpty()) {
            List<TelegramChat> admins = telegramRepository.fetchAdminChats();
            admins.forEach(admin -> {
                telegramBot.sendMessage(
                        admin.getChatId(), NotificationTemplates.NO_EXPIRED_BOOKINGS_MESSAGE);
            });
        } else {
            expiringBookings.forEach(bookingNotificationDto -> {
                sendNotification(bookingNotificationDto);
                sendNotification(bookingNotificationDto.accommodation());
            });
        }
    }

    @Async
    @Transactional
    @Override
    public void sendNotification(PaymentNotificationDto paymentNotificationDto) {
        Optional<TelegramChat> userChat = getChat(paymentNotificationDto.userId());
        userChat.ifPresent(
                c -> telegramBot.sendMessage(
                        c.getChatId(),
                        prepareNotification(paymentNotificationDto)));
    }

    private Optional<TelegramChat> getChat(Long userId) {
        return telegramRepository.findByUserId(userId);
    }

    private String prepareNotification(
            TelegramChat userChat,
            BookingNotificationDto bookingNotificationDto
    ) {
        String notification = bookingNotificationServiceStrategy
                .getNotificationService(bookingNotificationDto.bookingStatus()).getNotification()
                + NotificationTemplates.NOTIFICATION_BOOKING_DETAILS_TEMPLATE
                + prepareNotification(bookingNotificationDto.accommodation());

        return String.format(notification,
                userChat.getUser().getFirstName(),
                userChat.getUser().getLastName(),
                bookingNotificationDto.bookingStatus(),
                bookingNotificationDto.checkInDate(),
                bookingNotificationDto.checkOutDate());
    }

    private String prepareNotification(AccommodationNotificationDto accommodationNotificationDto) {
        return String.format(NotificationTemplates.NOTIFICATION_ACCOMMODATION_DETAILS_TEMPLATE,
                accommodationNotificationDto.accommodationTypeName(),
                accommodationNotificationDto.accommodationSize(),
                accommodationNotificationDto.amenities(),
                accommodationNotificationDto.location());
    }

    private String prepareNotification(PaymentNotificationDto paymentNotificationDto) {
        String notification = paymentNotificationServiceStrategy
                .getNotificationService(paymentNotificationDto.paymentStatus()).getNotification();
        return String.format(notification,
                paymentNotificationDto.firstName(),
                paymentNotificationDto.lastName(),
                paymentNotificationDto.bookingId());
    }
}

package bookingapp.service.impl;

import bookingapp.dto.payment.PaymentRequestDto;
import bookingapp.dto.payment.PaymentResponse;
import bookingapp.exception.AccessDeniedException;
import bookingapp.exception.EntityNotFoundException;
import bookingapp.exception.IllegalStateException;
import bookingapp.exception.UrlCreationException;
import bookingapp.mapper.PaymentMapper;
import bookingapp.model.booking.Booking;
import bookingapp.model.payment.Payment;
import bookingapp.model.user.User;
import bookingapp.repository.booking.BookingRepository;
import bookingapp.repository.payment.PaymentRepository;
import bookingapp.service.NotificationService;
import bookingapp.service.PaymentService;
import com.stripe.model.checkout.Session;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Booking.Status CONFIRMED = Booking.Status.CONFIRMED;
    private static final Payment.Status PENDING = Payment.Status.PENDING;
    private static final Payment.Status PAID = Payment.Status.PAID;
    private static final Payment.Status EXPIRED = Payment.Status.EXPIRED;
    private static final String SESSION_PAYMENT_STATUS_PAID = "paid";
    private static final String SESSION_STATUS_EXPIRED = "expired";
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final StripeService stripeService;

    @Override
    public List<PaymentResponse> getPayments(Long userId, Pageable pageable) {
        if (userId != null) {
            return paymentMapper.toDto(paymentRepository.findByBookingUserId(userId, pageable));
        }
        return paymentMapper.toDto(paymentRepository.findAll(pageable));
    }

    @Transactional
    @Override
    public PaymentResponse initiatePayment(User user, PaymentRequestDto requestDto) {
        Payment payment = paymentMapper.toModel(requestDto);
        Booking booking = bookingRepository
                .findByIdAndUserId(payment.getBooking().getId(), user.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't fetch booking by id: " + requestDto.bookingId()));
        payment.setStatus(PENDING);
        payment.setBooking(booking);
        payment.setAmountToPay(calculateAmount(booking));
        Session session = stripeService.createSession(payment);
        payment.setSessionUrl(getUrl(session.getUrl()));
        payment.setSessionId(session.getId());
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Transactional
    @Override
    public PaymentResponse handleSuccessPayment(String sessionId) {
        Payment payment = getPayment(sessionId);
        Session session = stripeService.getSessionById(sessionId);
        if (session != null && session.getPaymentStatus().equals(SESSION_PAYMENT_STATUS_PAID)) {
            Booking booking = payment.getBooking();
            booking.setStatus(CONFIRMED);
            payment.setStatus(PAID);
            bookingRepository.save(booking);
            paymentRepository.save(payment);
        }
        sendNotification(payment);
        return paymentMapper.toDto(payment);
    }

    @Transactional
    @Override
    public PaymentResponse handleCancelPayment(String sessionId) {
        Payment payment = getPayment(sessionId);
        sendNotification(payment);
        return paymentMapper.toDto(payment);
    }

    @Transactional
    @Override
    public void processExpiredPayments() {
        paymentRepository.findPendingPayments().forEach(p -> {
            Session session = stripeService.getSessionById(p.getSessionId());
            if (session != null && session.getStatus().equals(SESSION_STATUS_EXPIRED)) {
                p.setStatus(EXPIRED);
                paymentRepository.save(p);
            }
        });
    }

    @Transactional
    @Override
    public PaymentResponse renewPaymentSession(String sessionId, User user) {
        Payment payment = getPayment(sessionId);
        if (!payment.getBooking().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Can't renew payment session. "
                    + "You're not authorized to renew payment");
        }
        if (!payment.getStatus().equals(EXPIRED)) {
            throw new IllegalStateException("Can't renew payment session. Is not expired");
        }
        Session newSession = stripeService.createSession(payment);
        payment.setSessionUrl(getUrl(newSession.getUrl()));
        payment.setSessionId(newSession.getId());
        payment.setStatus(PENDING);
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    private Payment getPayment(String sessionId) {
        return paymentRepository.findBySessionId(sessionId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't retrieve payment by session id: " + sessionId));
    }

    private BigDecimal calculateAmount(Booking booking) {
        long days = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        return BigDecimal.valueOf(days).multiply(booking.getAccommodation().getDailyRate());
    }

    private URL getUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException ex) {
            throw new UrlCreationException("URL creation failure. " + ex.getMessage());
        }
    }

    private void sendNotification(Payment payment) {
        notificationService.sendNotification(payment);
    }
}

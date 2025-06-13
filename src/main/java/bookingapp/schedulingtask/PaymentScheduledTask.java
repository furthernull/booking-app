package bookingapp.schedulingtask;

import bookingapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentScheduledTask {
    private final PaymentService paymentService;

    @Scheduled(fixedRateString = "${payment.check.rate}")
    public void scheduleExpiredPayment() {
        paymentService.processExpiredPayments();
    }
}

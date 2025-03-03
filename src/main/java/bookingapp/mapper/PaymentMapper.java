package bookingapp.mapper;

import bookingapp.config.MapperConfig;
import bookingapp.dto.payment.PaymentNotificationDto;
import bookingapp.dto.payment.PaymentRequestDto;
import bookingapp.dto.payment.PaymentResponse;
import bookingapp.model.payment.Payment;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "booking.id", source = "requestDto.bookingId")
    @Mapping(target = "sessionUrl", ignore = true)
    Payment toModel(PaymentRequestDto requestDto);

    @Mapping(target = "paymentStatus", source = "payment.status")
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "amount", source = "amountToPay")
    PaymentResponse toDto(Payment payment);

    List<PaymentResponse> toDto(Iterable<Payment> payments);

    @Mapping(target = "userId", source = "payment.booking.user.id")
    @Mapping(target = "bookingId", source = "payment.booking.id")
    @Mapping(target = "paymentStatus", source = "payment.status")
    @Mapping(target = "firstName", source = "payment.booking.user.firstName")
    @Mapping(target = "lastName", source = "payment.booking.user.lastName")
    PaymentNotificationDto toNotificationDto(Payment payment);
}

package bookingapp.repository.booking.spec;

import static bookingapp.repository.booking.BookingSpecificationBuilder.USER_KEY;

import bookingapp.model.booking.Booking;
import bookingapp.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecificationProvider implements SpecificationProvider<Booking> {
    @Override
    public String getKey() {
        return USER_KEY;
    }

    @Override
    public Specification<Booking> getSpecification(String param) {
        long userId = Long.parseLong(param);
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(USER_KEY).get("id"), userId);
    }
}

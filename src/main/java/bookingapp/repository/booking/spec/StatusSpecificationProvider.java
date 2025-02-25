package bookingapp.repository.booking.spec;

import bookingapp.model.booking.Booking;
import bookingapp.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class StatusSpecificationProvider implements SpecificationProvider<Booking> {

    @Override
    public String getKey() {
        return "status";
    }

    @Override
    public Specification<Booking> getSpecification(String param) {
        Booking.Status status = Booking.Status.valueOf(param);
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("status"), status);
    }
}

package bookingapp.repository.booking;

import bookingapp.model.booking.Booking;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository
        extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    @EntityGraph(attributePaths = {"accommodation", "user"})
    List<Booking> findAllByUserId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"accommodation", "user"})
    Optional<Booking> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.status = 'PENDING'")
    List<Booking> findAllPendingBookingByUserId(Long userId);

    @Query("SELECT b FROM Booking b WHERE b.accommodation.id = :accommodationId "
            + "AND b.status != :cancelledStatus "
            + "AND b.isDeleted = FALSE "
            + "AND (b.checkInDate < :endDate AND b.checkOutDate > :startDate)")
    List<Booking> findConflictingBooking(
            Long accommodationId,
            LocalDate startDate,
            LocalDate endDate,
            Booking.Status cancelledStatus);

    @Query("SELECT b FROM Booking b "
            + "JOIN FETCH b.user "
            + "JOIN FETCH b.accommodation a "
            + "JOIN FETCH a.amenities "
            + "JOIN FETCH a.location WHERE b.id = :id")
    Optional<Booking> findById(Long id);

    @Query("SELECT b FROM Booking b "
            + "JOIN FETCH b.accommodation a "
            + "JOIN FETCH a.amenities "
            + "JOIN a.location "
            + "JOIN FETCH b.user "
            + "WHERE b.status NOT IN ('CANCELED', 'EXPIRED') AND b.checkOutDate <= :date")
    List<Booking> findExpiringBookings(LocalDate date);
}

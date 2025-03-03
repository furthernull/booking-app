package bookingapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bookingapp.model.booking.Booking;
import bookingapp.repository.booking.BookingRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:database/booking/delete-booking-related-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:database/booking/add-default-booking.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:database/booking/delete-booking-related-data.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    @DisplayName("Verify findAllByUserId() method")
    void findAllByUserId_ValidUserId_ReturnsBookings() {
        // Given
        Long userId = 2L;
        Pageable pageable = PageRequest.of(0, 10);

        // When
        List<Booking> bookings = bookingRepository.findAllByUserId(userId, pageable);

        // Then
        assertEquals(1, bookings.size());
        assertEquals(userId, bookings.get(0).getUser().getId());
    }

    @Test
    @DisplayName("Verify findByIdAndUserId() method")
    void findByIdAndUserId_ValidIdAndUserId_ReturnsBookings() {
        // Given
        Long bookingId = 1L;
        Long userId = 2L;

        // When
        Optional<Booking> booking = bookingRepository.findByIdAndUserId(bookingId, userId);

        // Then
        assertTrue(booking.isPresent());
        assertEquals(bookingId, booking.get().getId());
        assertEquals(userId, booking.get().getUser().getId());
    }

    @Test
    @DisplayName("Verify findConflictingBooking() method")
    void findConflictingBooking_ValidBookedDates_ReturnFalse() {
        // Given
        Long accommodationId = 1L;
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusMonths(1);
        Booking.Status cancelled = Booking.Status.CANCELLED;

        // When
        List<Booking> bookings = bookingRepository
                .findConflictingBooking(accommodationId, startDate, endDate, cancelled);

        // Then
        assertFalse(bookings.isEmpty());
    }

    @Test
    @DisplayName("Verify findById() method")
    void findById_ValidId_ReturnsValidBooking() {
        // Given
        Long bookingId = 1L;

        // When
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        // Then
        assertTrue(booking.isPresent());
        assertEquals(bookingId, booking.get().getId());
    }

    @Test
    @DisplayName("Verify findExpiringBookings")
    void findExpiringBookings_ValidDate_ReturnEmptyList() {
        // Given
        LocalDate today = LocalDate.now();

        // When
        List<Booking> bookings = bookingRepository.findExpiringBookings(today);

        // Then
        assertTrue(bookings.isEmpty());
    }
}

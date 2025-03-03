package bookingapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bookingapp.model.user.User;
import bookingapp.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:database/user/delete-user-related-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:database/user/add-default-users.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/user/delete-user-related-data.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Verify existByEmail() method should return true for existing user")
    void existByEmail_ValidEmail_ReturnTrue() {
        // Given
        String existingEmail = "john.doe@example.com";

        // When
        boolean exists = userRepository.existsByEmail(existingEmail);

        // Then
        assertTrue(exists, "User with the given email should exist in the database");
    }

    @Test
    @DisplayName("Verify existByEmail() method should return false for non-existing user")
    void existByEmail_NotValidEmail_ReturnFalse() {
        // Given
        String nonExistingEmail = "noexist@test.com";

        // When
        boolean exists = userRepository.existsByEmail(nonExistingEmail);

        // Then
        assertFalse(exists, "User with the given email should not exist in the database");
    }

    @Test
    @DisplayName("Verify findByEmail() method should return an existing user")
    void findByEmail_ValidEmail_ReturnExistingUser() {
        // Given
        String existingEmail = "john.doe@example.com";

        // When
        Optional<User> foundUser = userRepository.findByEmail(existingEmail);

        // Then
        assertTrue(foundUser.isPresent(), "User should be found in the database");
        assertEquals(existingEmail, foundUser.get().getEmail(), "Emails should match");
    }

    @Test
    @DisplayName("Verify findByEmail() method "
            + "should return an empty optional for a non-existing user")
    void findByEmail_NotValidEmail_ReturnEmptyUser() {
        // Given
        String nonExistingEmail = "noexist@test.com";

        // When
        Optional<User> foundUser = userRepository.findByEmail(nonExistingEmail);

        // Then
        assertFalse(foundUser.isPresent(), "No user should be found for a non-existing email");
    }
}

package bookingapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bookingapp.model.user.Role;
import bookingapp.repository.role.RoleRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Verify findByRole() method could return valid")
    void findByRole_ValidRole_ReturnsRole() {
        // Given
        Role.RoleName expectedRole = Role.RoleName.CUSTOMER;

        // When
        Optional<Role> foundRole = roleRepository.findByRole(expectedRole);

        // Then
        assertTrue(foundRole.isPresent(), "Role should be present in the database");
        assertEquals(expectedRole,
                foundRole.get().getRole(),
                "Returned role should match the expected role");
    }
}

package bookingapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bookingapp.dto.user.UserResponseDto;
import bookingapp.dto.user.UserUpdateRequestDto;
import bookingapp.dto.user.UserUpdateRoleRequestDto;
import bookingapp.model.user.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UserControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext,
            @Autowired DataSource dataSource
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        teardown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/user/add-default-users.sql")
            );
        }
    }

    @AfterAll
    static void afterAll(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @SneakyThrows
    private static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/user/delete-user-related-data.sql")
            );
        }
    }

    @Test
    @DisplayName("Verify updateRole() method")
    @WithUserDetails(value = "admin@email.com")
    void updateRole_ValidUserUpdateRoleRequestDto_ReturnValidUserResponseDto() throws Exception {
        // Given
        Long userId = 2L;
        UserUpdateRoleRequestDto updateRoleRequestDto =
                new UserUpdateRoleRequestDto(Role.RoleName.ADMIN);
        String jsonRequest = objectMapper.writeValueAsString(updateRoleRequestDto);

        // When
        MvcResult result = mockMvc.perform(put("/users/{id}/role", userId)
                        .content(jsonRequest)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        UserResponseDto userResponseDto = objectMapper
                .readValue(result.getResponse().getContentAsString(), UserResponseDto.class);
        assertNotNull(userResponseDto);
    }

    @Test
    @DisplayName("Verify getInfo() method")
    @WithUserDetails(value = "john.doe@example.com",
            userDetailsServiceBeanName = "customUserDetailsService")
    void getInfo_ValidAuthenticatedUser_ReturnUserResponseDto() throws Exception {
        // Given
        String expectedEmail = "john.doe@example.com";

        // When
        MvcResult result = mockMvc.perform(get("/users/me")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        UserResponseDto userResponseDto = objectMapper
                .readValue(result.getResponse().getContentAsString(), UserResponseDto.class);
        assertNotNull(userResponseDto);
        assertEquals(expectedEmail, userResponseDto.email());
    }

    @Test
    @DisplayName("Verify updateUser() method")
    @WithUserDetails(value = "john.doe@example.com",
            userDetailsServiceBeanName = "customUserDetailsService")
    @Sql(scripts = "classpath:database/user/add-user-for-update.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateUser_ValidUserUpdateRequestDto_ReturnValidUserResponseDto() throws Exception {
        // Given
        UserUpdateRequestDto requestDto = new UserUpdateRequestDto("Jane", "Doe");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // When
        MvcResult result = mockMvc.perform(patch("/users/me")
                        .content(jsonRequest)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        UserResponseDto userResponseDto = objectMapper
                .readValue(result.getResponse().getContentAsString(), UserResponseDto.class);
        assertNotNull(userResponseDto);
        assertEquals("Jane", userResponseDto.firstName());
        assertEquals("Doe", userResponseDto.lastName());
    }
}

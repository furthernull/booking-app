package bookingapp.controller;

import static bookingapp.test.TestUtils.ACCOMMODATION_REQUEST_DTO_STUDIO;
import static bookingapp.test.TestUtils.TYPE_CONDO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bookingapp.dto.accommodation.AccommodationDto;
import bookingapp.dto.accommodation.AccommodationRequestDto;
import bookingapp.dto.address.AddressRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccommodationControllerTest {
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
                    new ClassPathResource(
                            "database/accommodation/add-default-two-accommodation.sql")
            );
        }
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource(
                            "database/accommodation/delete-accommodation-related-data.sql")
            );
        }
    }

    @Test
    @DisplayName("Verify create() method")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:database/accommodation/delete-added-accommodation.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_ValidAccommodationRequest_ReturnValidResponse() throws Exception {
        // Given
        String jsonRequest = objectMapper.writeValueAsString(ACCOMMODATION_REQUEST_DTO_STUDIO);

        // When
        MvcResult result = mockMvc.perform(post("/accommodations")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        AccommodationDto accommodationDto = objectMapper.readValue(
                result.getResponse().getContentAsString(), AccommodationDto.class);
        assertNotNull(accommodationDto);
        System.out.println(accommodationDto);
    }

    @Test
    @DisplayName("Verify getAll() method")
    void getAll_ShouldReturnTwoAccommodations() throws Exception {
        // When
        MvcResult result = mockMvc.perform(get("/accommodations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        AccommodationDto[] accommodationDtos = objectMapper
                .readValue(result.getResponse().getContentAsString(),
                AccommodationDto[].class);
        assertEquals(2, accommodationDtos.length);
        assertEquals(1L, accommodationDtos[0].getId());
    }

    @Test
    @DisplayName("Verify getById() method")
    void getById_ShouldReturnAccommodation() throws Exception {
        // Given
        Long id = 1L;

        // When
        MvcResult result = mockMvc.perform(get("/accommodations/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        AccommodationDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                AccommodationDto.class);
        assertNotNull(actual);
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("Verify update() method")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void update_ValidAccommodationRequest_ReturnUpdatedAccommodation() throws Exception {
        // Given
        Long id = 2L;
        AccommodationRequestDto updateRequestDto = new AccommodationRequestDto(
                TYPE_CONDO,
                new AddressRequestDto(
                        "23 Shevchenko",
                        "Lviv",
                        "Lviv",
                        "79000",
                        "Ukraine"),
                "1 Bedroom",
                Set.of(5L),
                BigDecimal.ONE,
                1
        );
        String jsonRequest = objectMapper.writeValueAsString(updateRequestDto);

        // When
        MvcResult result = mockMvc.perform(put("/accommodations/{id}", id)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        AccommodationDto accommodationDto = objectMapper.readValue(
                result.getResponse().getContentAsString(), AccommodationDto.class);
        assertNotNull(accommodationDto);
    }

    @Test
    @DisplayName("Verify delete() method")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:database/accommodation/add-temporary-accommodation.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete_ValidId_ShouldDeleteAccommodation() throws Exception {
        // Given
        Long id = 3L;

        // When & Then
        mockMvc.perform(delete("/accommodations/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

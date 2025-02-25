package bookingapp.test;

import bookingapp.dto.accommodation.AccommodationDto;
import bookingapp.dto.accommodation.AccommodationRequestDto;
import bookingapp.dto.address.AddressRequestDto;
import bookingapp.dto.booking.BookingFilterParameters;
import bookingapp.dto.booking.BookingRequestDto;
import bookingapp.dto.booking.BookingResponseDto;
import bookingapp.dto.booking.BookingUpdateRequestDto;
import bookingapp.dto.payment.PaymentRequestDto;
import bookingapp.dto.payment.PaymentResponse;
import bookingapp.dto.user.UserRegistrationRequestDto;
import bookingapp.dto.user.UserResponseDto;
import bookingapp.dto.user.UserUpdateRequestDto;
import bookingapp.dto.user.UserUpdateRoleRequestDto;
import bookingapp.model.accommodation.Accommodation;
import bookingapp.model.accommodation.Address;
import bookingapp.model.accommodation.AmenityType;
import bookingapp.model.booking.Booking;
import bookingapp.model.payment.Payment;
import bookingapp.model.telegram.TelegramChat;
import bookingapp.model.user.Role;
import bookingapp.model.user.User;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class TestUtils {
    public static final int AVAILABILITY_COUNT = 1;

    public static final BigDecimal DEFAULT_DAILY_RATE = BigDecimal.TEN;
    public static final BigDecimal DEFAULT_DAILY_RATE_2 = BigDecimal.valueOf(25.00);

    public static final Long DEFAULT_ID_ONE = 1L;
    public static final Long DEFAULT_ID_TWO = 2L;
    public static final Long DEFAULT_ID_THREE = 3L;
    public static final Long DEFAULT_ID_FOUR = 4L;
    public static final Long DEFAULT_ID_FIVE = 5L;
    public static final Long DEFAULT_ID_SIX = 6L;
    public static final Long DEFAULT_ID_SEVEN = 7L;
    public static final Long DEFAULT_ID_EIGHT = 8L;

    public static final String ADDRESS_FIELD = "Address";
    public static final String CITY_FIELD = "City";
    public static final String STATE_FIELD = "State";
    public static final String ZIP_FIELD = "12345";
    public static final String COUNTRY_FIELD = "Country";

    public static final String ADMIN_FIRST_NAME_FIELD = "Admin";
    public static final String ADMIN_LAST_NAME_FIELD = "Admin";
    public static final String ADMIN_EMAIL_FIELD = "admin@bookingapp.com";
    public static final String ADMIN_PASSWORD_FIELD = "Qwerty&0";

    public static final String CUSTOMER_FIRST_NAME_FIELD = "John";
    public static final String CUSTOMER_LAST_NAME_FIELD = "Doe";
    public static final String CUSTOMER_EMAIL_FIELD = "john.doe@example.com";
    public static final String CUSTOMER_PASSWORD_FIELD = "Qwerty&0";
    public static final String CUSTOMER_PASSWORD_CONFIRMATION_FIELD = "Qwerty&0";
    public static final String CUSTOMER_ENCODED_PASSWORD =
            "$2a$10$yvofprfFFpxZatoflOuduupLzGqFWizREUJ0.lcQMDREJ28r8XB6.";

    public static final String SECOND_CUSTOMER_FIRST_NAME_FIELD = "Jane";
    public static final String SECOND_CUSTOMER_LAST_NAME_FIELD = "Doe";
    public static final String SECOND_CUSTOMER_EMAIL_FIELD = "jane.doe@example.com";
    public static final String SECOND_CUSTOMER_PASSWORD_FIELD = "AnotherCustomerPassword!1";
    public static final String SECOND_CUSTOMER_PASSWORD_CONFIRMATION_FIELD =
            "AnotherCustomerPassword!1";

    public static final String ACCOMMODATION_SIZE_STUDIO = "Studio";
    public static final String ACCOMMODATION_SIZE_ONE_BEDROOM = "1 Bedroom";
    public static final String ACCOMMODATION_SIZE_TWO_BEDROOM = "2 Bedroom";

    public static final Accommodation.Type TYPE_APARTMENT =
            Accommodation.Type.valueOf("APARTMENT");
    public static final Accommodation.Type TYPE_CONDO =
            Accommodation.Type.valueOf("CONDO");
    public static final Accommodation.Type TYPE_HOUSE =
            Accommodation.Type.valueOf("HOUSE");

    public static final AddressRequestDto ADDRESS_REQUEST_DTO = new AddressRequestDto(
            ADDRESS_FIELD, CITY_FIELD, STATE_FIELD, ZIP_FIELD, COUNTRY_FIELD
    );

    public static final Address ADDRESS = getAddress();

    public static final AmenityType.Type TYPE_PARKING = AmenityType.Type.PARKING;
    public static final AmenityType.Type TYPE_CAR_CHARGER = AmenityType.Type.CAR_CHARGER;
    public static final AmenityType.Type TYPE_PETS = AmenityType.Type.PETS;
    public static final AmenityType.Type TYPE_WI_FI = AmenityType.Type.WI_FI;
    public static final AmenityType.Type TYPE_POOL = AmenityType.Type.POOL;
    public static final AmenityType.Type TYPE_SPA = AmenityType.Type.SPA;
    public static final AmenityType.Type TYPE_GYM = AmenityType.Type.GYM;
    public static final AmenityType.Type TYPE_CAFE = AmenityType.Type.CAFE;

    public static final AmenityType AMENITY_TYPE_PARKING = getAmenityTypeParking();
    public static final AmenityType AMENITY_TYPE_CAR_CHARGER = getAmenityTypeCarCharger();
    public static final AmenityType AMENITY_TYPE_PETS = getAmenityTypePets();
    public static final AmenityType AMENITY_TYPE_WI_FI = getAmenityTypeWiFi();
    public static final AmenityType AMENITY_TYPE_POOL = getAmenityTypePool();
    public static final AmenityType AMENITY_TYPE_SPA = getAmenityTypeSpa();
    public static final AmenityType AMENITY_TYPE_GYM = getAmenityTypeGym();
    public static final AmenityType AMENITY_TYPE_CAFE = getAmenityTypeCafe();

    public static final Set<Long> AMENITY_IDS_SET = Set.of(
            DEFAULT_ID_ONE,
            DEFAULT_ID_TWO,
            DEFAULT_ID_THREE,
            DEFAULT_ID_FOUR,
            DEFAULT_ID_FIVE,
            DEFAULT_ID_SIX,
            DEFAULT_ID_SEVEN,
            DEFAULT_ID_EIGHT
    );

    public static final Set<AmenityType> AMENITY_TYPE_SET = Set.of(
            AMENITY_TYPE_PARKING,
            AMENITY_TYPE_CAR_CHARGER,
            AMENITY_TYPE_PETS,
            AMENITY_TYPE_WI_FI,
            AMENITY_TYPE_POOL,
            AMENITY_TYPE_SPA,
            AMENITY_TYPE_GYM,
            AMENITY_TYPE_CAFE
    );

    public static final AccommodationRequestDto ACCOMMODATION_REQUEST_DTO_STUDIO =
            new AccommodationRequestDto(
                    TYPE_APARTMENT,
                    ADDRESS_REQUEST_DTO,
                    ACCOMMODATION_SIZE_STUDIO,
                    AMENITY_IDS_SET,
                    DEFAULT_DAILY_RATE,
                    AVAILABILITY_COUNT
            );

    public static final AccommodationRequestDto ACCOMMODATION_UPDATE_REQUEST_DTO_HOUSE =
            new AccommodationRequestDto(
                    TYPE_HOUSE,
                    ADDRESS_REQUEST_DTO,
                    ACCOMMODATION_SIZE_TWO_BEDROOM,
                    AMENITY_IDS_SET,
                    DEFAULT_DAILY_RATE_2,
                    AVAILABILITY_COUNT
            );

    public static final Accommodation ACCOMMODATION_STUDIO = getAccommodationStudio();
    public static final Accommodation ACCOMMODATION_UPDATED_TO_HOUSE =
            getAccommodationUpdatedToHouse();
    public static final Accommodation ACCOMMODATION_CONDO = getAccommodationCondo();

    public static final AccommodationDto ACCOMMODATION_DTO_STUDIO = getAccommodationDtoStudio();
    public static final AccommodationDto ACCOMMODATION_DTO_UPDATED_TO_HOUSE =
            getAccommodationDtoUpdatedToHouse();

    public static final Pageable PAGEABLE = PageRequest.of(0, 10);
    public static final List<Accommodation> ACCOMMODATION_LIST = List.of(ACCOMMODATION_STUDIO);
    public static final Page<Accommodation> ACCOMMODATION_PAGE = new PageImpl<>(ACCOMMODATION_LIST);

    public static final Role.RoleName ROLE_NAME_ADMIN = Role.RoleName.ADMIN;
    public static final Role.RoleName ROLE_NAME_CUSTOMER = Role.RoleName.CUSTOMER;

    public static final Role ROLE_ADMIN = getRoleAdmin();
    public static final Role ROLE_CUSTOMER = getRoleCustomer();

    public static final UserRegistrationRequestDto USER_REGISTRATION_REQUEST_DTO =
            new UserRegistrationRequestDto(
                    CUSTOMER_EMAIL_FIELD,
                    CUSTOMER_FIRST_NAME_FIELD,
                    CUSTOMER_LAST_NAME_FIELD,
                    CUSTOMER_PASSWORD_FIELD,
                    CUSTOMER_PASSWORD_CONFIRMATION_FIELD
            );

    public static final UserRegistrationRequestDto SECOND_USER_REGISTRATION_REQUEST_DTO =
            new UserRegistrationRequestDto(
                    SECOND_CUSTOMER_EMAIL_FIELD,
                    SECOND_CUSTOMER_FIRST_NAME_FIELD,
                    SECOND_CUSTOMER_LAST_NAME_FIELD,
                    SECOND_CUSTOMER_PASSWORD_FIELD,
                    SECOND_CUSTOMER_PASSWORD_CONFIRMATION_FIELD
            );

    public static final UserUpdateRoleRequestDto USER_UPDATE_ROLE_REQUEST_DTO =
            new UserUpdateRoleRequestDto(Role.RoleName.ADMIN);

    public static final UserUpdateRequestDto USER_UPDATE_REQUEST_DTO =
            new UserUpdateRequestDto(
                    SECOND_CUSTOMER_FIRST_NAME_FIELD,
                    SECOND_CUSTOMER_LAST_NAME_FIELD
            );

    public static final User USER_ADMIN = getUserAdmin();
    public static final User USER_CUSTOMER = getUserCustomer();
    public static final User USER_CUSTOMER_2 = getUserCustomer2();
    public static final User USER_CUSTOMER_UPDATED_ROLE = getUserCustomerUpdatedRole();

    public static final UserResponseDto USER_ADMIN_RESPONSE_DTO = new UserResponseDto(
            DEFAULT_ID_ONE,
            ADMIN_FIRST_NAME_FIELD,
            ADMIN_LAST_NAME_FIELD,
            ADMIN_EMAIL_FIELD
    );

    public static final UserResponseDto USER_RESPONSE_DTO = new UserResponseDto(
            DEFAULT_ID_TWO,
            CUSTOMER_FIRST_NAME_FIELD,
            CUSTOMER_LAST_NAME_FIELD,
            CUSTOMER_EMAIL_FIELD
    );

    public static final UserResponseDto SECOND_USER_RESPONSE_DTO = new UserResponseDto(
            DEFAULT_ID_THREE,
            SECOND_CUSTOMER_FIRST_NAME_FIELD,
            SECOND_CUSTOMER_LAST_NAME_FIELD,
            SECOND_CUSTOMER_EMAIL_FIELD
    );

    public static final Booking.Status BOOKING_STATUS_PENDING = Booking.Status.PENDING;
    public static final Booking.Status BOOKING_STATUS_CONFIRMED = Booking.Status.CONFIRMED;
    public static final Booking.Status BOOKING_STATUS_CANCELLED = Booking.Status.CANCELLED;
    public static final Booking.Status BOOKING_STATUS_EXPIRED = Booking.Status.EXPIRED;

    public static final LocalDate DEFAULT_CHECK_IN_DATE = LocalDate.now().plusDays(1);
    public static final LocalDate DEFAULT_CHECK_OUT_DATE = LocalDate.now().plusMonths(1);
    public static final LocalDate DEFAULT_CHECK_IN_DATE_DIFFERENT = LocalDate.now().plusMonths(1);
    public static final LocalDate DEFAULT_CHECK_OUT_DATE_DIFFERENT = LocalDate.now().plusYears(1);
    public static final LocalDate UPDATE_CHECK_IN_DATE = LocalDate.now().plusWeeks(2);
    public static final LocalDate UPDATE_CHECK_OUT_DATE = LocalDate.now().plusYears(1);
    public static final LocalDate EXPIRED_CHECK_IN_DATE = LocalDate.now();
    public static final LocalDate EXPIRED_CHECK_OUT_DATE = LocalDate.now().plusDays(1);

    public static final BookingRequestDto BOOKING_STUDIO_REQUEST_DTO = new BookingRequestDto(
            DEFAULT_ID_ONE,
            DEFAULT_CHECK_IN_DATE,
            DEFAULT_CHECK_OUT_DATE
    );

    public static final Booking BOOKING_STUDIO_PENDING = getBookingStudioPending();
    public static final Booking BOOKING_STUDIO_CONFIRMED = getBookingStudioConfirmed();

    public static final BookingResponseDto BOOKING_STUDIO_RESPONSE_DTO =
            new BookingResponseDto(
                DEFAULT_ID_ONE,
                DEFAULT_CHECK_IN_DATE,
                DEFAULT_CHECK_OUT_DATE,
                DEFAULT_ID_ONE,
                DEFAULT_ID_TWO,
                BOOKING_STUDIO_PENDING.getStatus().name()
    );

    public static final BookingFilterParameters BOOKING_FILTER_PARAMETERS =
            new BookingFilterParameters(
                    USER_CUSTOMER.getId(),
                    BOOKING_STUDIO_PENDING.getStatus()
            );

    public static final List<Booking> BOOKING_LIST = List.of(BOOKING_STUDIO_PENDING);
    public static final Page<Booking> BOOKING_PAGE = new PageImpl<>(BOOKING_LIST);

    public static final BookingUpdateRequestDto BOOKING_UPDATE_REQUEST_DTO =
            new BookingUpdateRequestDto(
                    UPDATE_CHECK_IN_DATE,
                    UPDATE_CHECK_OUT_DATE
            );

    public static final Booking BOOKING_STUDIO_UPDATED = getUpdatedBookingStudio();

    public static final BookingResponseDto BOOKING_STUDIO_UPDATED_RESPONSE_DTO =
            new BookingResponseDto(
                    DEFAULT_ID_ONE,
                    UPDATE_CHECK_IN_DATE,
                    UPDATE_CHECK_OUT_DATE,
                    DEFAULT_ID_ONE,
                    DEFAULT_ID_TWO,
                    BOOKING_STUDIO_PENDING.getStatus().name()
            );

    public static final Booking CONFLICTING_BOOKING = getConflictingBooking();

    public static final Booking BOOKING_STUDIO_CANCELED = getCanceledBookingStudio();

    public static final Booking BOOKING_CONDO_EXPIRED = getExpiredBookingCondo();

    public static final Payment.Status PAYMENT_STATUS_PENDING = Payment.Status.PENDING;
    public static final Payment.Status PAYMENT_STATUS_PAID = Payment.Status.PAID;
    public static final Payment.Status PAYMENT_STATUS_EXPIRED = Payment.Status.EXPIRED;

    public static final String SESSION_URL = "http://www.example.com";
    public static final String SESSION_ID = "sessionId";
    public static final String INVALID_SESSION_ID = "InvalidSessionId";

    public static final BigDecimal PAYMENT_AMOUNT = BigDecimal.valueOf(100);

    public static final PaymentRequestDto PAYMENT_REQUEST_DTO = new PaymentRequestDto(
            DEFAULT_ID_ONE
    );

    public static final Payment PAYMENT_PENDING = getPaymentPending();
    public static final Payment PAYMENT_PAID = getPaymentPaid();
    public static final Payment PAYMENT_EXPIRED = getPaymentExpired();

    public static final PaymentResponse PAYMENT_PENDING_RESPONSE = getPaymentPendingResponse();
    public static final PaymentResponse RENEWED_PAYMENT_PENDING_RESPONSE =
            getRenewedPaymentPendingResponse();
    public static final PaymentResponse SECOND_PAYMENT_PENDING_RESPONSE =
            getSecondPaymentPendingResponse();
    public static final PaymentResponse PAYMENT_PAID_RESPONSE = getPaymentPaidResponse();

    public static final Page<Payment> PAYMENT_PAGE = new PageImpl<>(List.of(PAYMENT_PENDING));

    public static final TelegramChat SUBSCRIBED_TELEGRAM_CHAT = getSubscribedTelegramChat();
    public static final TelegramChat UNSUBSCRIBE_TELEGRAM_CHAT = getUnsubscribedTelegramChat();

    private TestUtils() {
    }

    private static Address getAddress() {
        Address address = new Address();
        address.setId(DEFAULT_ID_ONE);
        address.setAddress(ADDRESS_FIELD);
        address.setCity(CITY_FIELD);
        address.setState(STATE_FIELD);
        address.setZipCode(ZIP_FIELD);
        address.setCountry(COUNTRY_FIELD);
        return address;
    }

    private static AmenityType getAmenityTypeParking() {
        AmenityType amenityType = new AmenityType();
        amenityType.setId(DEFAULT_ID_ONE);
        amenityType.setName(TYPE_PARKING);
        return amenityType;
    }

    private static AmenityType getAmenityTypeCarCharger() {
        AmenityType amenityType = new AmenityType();
        amenityType.setId(DEFAULT_ID_TWO);
        amenityType.setName(TYPE_CAR_CHARGER);
        return amenityType;
    }

    private static AmenityType getAmenityTypePets() {
        AmenityType amenityType = new AmenityType();
        amenityType.setId(DEFAULT_ID_THREE);
        amenityType.setName(TYPE_PETS);
        return amenityType;
    }

    private static AmenityType getAmenityTypeWiFi() {
        AmenityType amenityType = new AmenityType();
        amenityType.setId(DEFAULT_ID_FOUR);
        amenityType.setName(TYPE_WI_FI);
        return amenityType;
    }

    private static AmenityType getAmenityTypePool() {
        AmenityType amenityType = new AmenityType();
        amenityType.setId(DEFAULT_ID_FIVE);
        amenityType.setName(TYPE_POOL);
        return amenityType;
    }

    private static AmenityType getAmenityTypeSpa() {
        AmenityType amenityType = new AmenityType();
        amenityType.setId(DEFAULT_ID_SIX);
        amenityType.setName(TYPE_SPA);
        return amenityType;
    }

    private static AmenityType getAmenityTypeGym() {
        AmenityType amenityType = new AmenityType();
        amenityType.setId(DEFAULT_ID_SEVEN);
        amenityType.setName(TYPE_GYM);
        return amenityType;
    }

    private static AmenityType getAmenityTypeCafe() {
        AmenityType amenityType = new AmenityType();
        amenityType.setId(DEFAULT_ID_EIGHT);
        amenityType.setName(TYPE_CAFE);
        return amenityType;
    }

    private static Accommodation getAccommodationStudio() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(DEFAULT_ID_ONE);
        accommodation.setType(TYPE_APARTMENT);
        accommodation.setLocation(ADDRESS);
        accommodation.setSize(ACCOMMODATION_SIZE_STUDIO);
        accommodation.setAmenities(AMENITY_TYPE_SET);
        accommodation.setDailyRate(DEFAULT_DAILY_RATE);
        accommodation.setAvailability(AVAILABILITY_COUNT);
        return accommodation;
    }

    private static Accommodation getAccommodationUpdatedToHouse() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(DEFAULT_ID_ONE);
        accommodation.setType(TYPE_HOUSE);
        accommodation.setLocation(ADDRESS);
        accommodation.setSize(ACCOMMODATION_SIZE_TWO_BEDROOM);
        accommodation.setAmenities(AMENITY_TYPE_SET);
        accommodation.setDailyRate(DEFAULT_DAILY_RATE_2);
        accommodation.setAvailability(AVAILABILITY_COUNT);
        return accommodation;
    }

    private static Accommodation getAccommodationCondo() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(DEFAULT_ID_TWO);
        accommodation.setType(TYPE_CONDO);
        accommodation.setLocation(ADDRESS);
        accommodation.setSize(ACCOMMODATION_SIZE_ONE_BEDROOM);
        accommodation.setAmenities(AMENITY_TYPE_SET);
        accommodation.setDailyRate(DEFAULT_DAILY_RATE);
        accommodation.setAvailability(AVAILABILITY_COUNT);
        return accommodation;
    }

    private static AccommodationDto getAccommodationDtoStudio() {
        AccommodationDto accommodationDto = new AccommodationDto();
        accommodationDto.setId(DEFAULT_ID_ONE);
        accommodationDto.setType(TYPE_APARTMENT.name());
        accommodationDto.setLocation(ADDRESS.toString());
        accommodationDto.setSize(ACCOMMODATION_SIZE_STUDIO);
        accommodationDto.setAmenityIds(AMENITY_IDS_SET);
        accommodationDto.setDailyRate(DEFAULT_DAILY_RATE);
        accommodationDto.setAvailability(AVAILABILITY_COUNT);
        return accommodationDto;
    }

    private static AccommodationDto getAccommodationDtoUpdatedToHouse() {
        AccommodationDto accommodationDto = new AccommodationDto();
        accommodationDto.setId(DEFAULT_ID_ONE);
        accommodationDto.setType(TYPE_HOUSE.name());
        accommodationDto.setLocation(ADDRESS.toString());
        accommodationDto.setSize(ACCOMMODATION_SIZE_TWO_BEDROOM);
        accommodationDto.setAmenityIds(AMENITY_IDS_SET);
        accommodationDto.setDailyRate(DEFAULT_DAILY_RATE_2);
        accommodationDto.setAvailability(AVAILABILITY_COUNT);
        return accommodationDto;
    }

    private static Role getRoleAdmin() {
        Role role = new Role();
        role.setId(DEFAULT_ID_ONE);
        role.setRole(ROLE_NAME_ADMIN);
        return role;
    }

    private static Role getRoleCustomer() {
        Role role = new Role();
        role.setId(DEFAULT_ID_TWO);
        role.setRole(ROLE_NAME_CUSTOMER);
        return role;
    }

    private static User getUserAdmin() {
        User user = new User();
        user.setId(DEFAULT_ID_ONE);
        user.setFirstName(ADMIN_FIRST_NAME_FIELD);
        user.setLastName(ADMIN_LAST_NAME_FIELD);
        user.setEmail(ADMIN_EMAIL_FIELD);
        user.setPassword(ADMIN_PASSWORD_FIELD);
        user.setRoles(Set.of(ROLE_ADMIN));
        return user;
    }

    private static User getUserCustomer() {
        User user = new User();
        user.setId(DEFAULT_ID_TWO);
        user.setFirstName(CUSTOMER_FIRST_NAME_FIELD);
        user.setLastName(CUSTOMER_LAST_NAME_FIELD);
        user.setEmail(CUSTOMER_EMAIL_FIELD);
        user.setPassword(CUSTOMER_PASSWORD_FIELD);
        user.setRoles(Set.of(ROLE_CUSTOMER));
        return user;
    }

    private static User getUserCustomer2() {
        User user = new User();
        user.setId(DEFAULT_ID_THREE);
        user.setFirstName(SECOND_CUSTOMER_FIRST_NAME_FIELD);
        user.setLastName(SECOND_CUSTOMER_LAST_NAME_FIELD);
        user.setEmail(SECOND_CUSTOMER_EMAIL_FIELD);
        user.setPassword(SECOND_CUSTOMER_PASSWORD_FIELD);
        user.setRoles(Set.of(ROLE_CUSTOMER));
        return user;
    }

    private static User getUserCustomerUpdatedRole() {
        User user = new User();
        user.setId(DEFAULT_ID_TWO);
        user.setFirstName(CUSTOMER_FIRST_NAME_FIELD);
        user.setLastName(CUSTOMER_LAST_NAME_FIELD);
        user.setEmail(CUSTOMER_EMAIL_FIELD);
        user.setPassword(CUSTOMER_PASSWORD_FIELD);
        user.setRoles(Set.of(ROLE_ADMIN));
        return user;
    }

    private static Booking getBookingStudioPending() {
        Booking booking = new Booking();
        booking.setId(DEFAULT_ID_ONE);
        booking.setCheckInDate(DEFAULT_CHECK_IN_DATE);
        booking.setCheckOutDate(DEFAULT_CHECK_OUT_DATE);
        booking.setAccommodation(ACCOMMODATION_STUDIO);
        booking.setUser(USER_CUSTOMER);
        booking.setStatus(BOOKING_STATUS_PENDING);
        return booking;
    }

    private static Booking getBookingStudioConfirmed() {
        Booking booking = new Booking();
        booking.setId(DEFAULT_ID_ONE);
        booking.setCheckInDate(DEFAULT_CHECK_IN_DATE);
        booking.setCheckOutDate(DEFAULT_CHECK_OUT_DATE);
        booking.setAccommodation(ACCOMMODATION_STUDIO);
        booking.setUser(USER_CUSTOMER);
        booking.setStatus(BOOKING_STATUS_CONFIRMED);
        return booking;
    }

    private static Booking getUpdatedBookingStudio() {
        Booking booking = new Booking();
        booking.setId(DEFAULT_ID_ONE);
        booking.setCheckInDate(UPDATE_CHECK_IN_DATE);
        booking.setCheckOutDate(UPDATE_CHECK_OUT_DATE);
        booking.setAccommodation(ACCOMMODATION_STUDIO);
        booking.setUser(USER_CUSTOMER);
        booking.setStatus(BOOKING_STATUS_PENDING);
        return booking;
    }

    private static Booking getConflictingBooking() {
        Booking booking = new Booking();
        booking.setId(DEFAULT_ID_TWO);
        booking.setCheckInDate(UPDATE_CHECK_IN_DATE);
        booking.setCheckOutDate(UPDATE_CHECK_OUT_DATE);
        booking.setAccommodation(ACCOMMODATION_STUDIO);
        booking.setUser(USER_CUSTOMER_2);
        booking.setStatus(BOOKING_STATUS_PENDING);
        return booking;
    }

    private static Booking getCanceledBookingStudio() {
        Booking booking = new Booking();
        booking.setId(DEFAULT_ID_ONE);
        booking.setCheckInDate(DEFAULT_CHECK_IN_DATE);
        booking.setCheckOutDate(DEFAULT_CHECK_OUT_DATE);
        booking.setAccommodation(ACCOMMODATION_STUDIO);
        booking.setUser(USER_CUSTOMER);
        booking.setStatus(BOOKING_STATUS_CANCELLED);
        return booking;
    }

    private static Booking getExpiredBookingCondo() {
        Booking booking = new Booking();
        booking.setId(DEFAULT_ID_THREE);
        booking.setCheckInDate(EXPIRED_CHECK_IN_DATE);
        booking.setCheckOutDate(EXPIRED_CHECK_OUT_DATE);
        booking.setAccommodation(ACCOMMODATION_CONDO);
        booking.setUser(USER_CUSTOMER_2);
        booking.setStatus(BOOKING_STATUS_EXPIRED);
        return booking;
    }

    @SneakyThrows
    private static Payment getPaymentPending() {
        Payment payment = new Payment();
        payment.setId(DEFAULT_ID_ONE);
        payment.setStatus(PAYMENT_STATUS_PENDING);
        payment.setBooking(BOOKING_STUDIO_PENDING);
        payment.setSessionUrl(new URL(SESSION_URL));
        payment.setSessionId(SESSION_ID);
        payment.setAmountToPay(PAYMENT_AMOUNT);
        return payment;
    }

    @SneakyThrows
    private static Payment getPaymentPaid() {
        Payment payment = new Payment();
        payment.setId(DEFAULT_ID_ONE);
        payment.setStatus(PAYMENT_STATUS_PAID);
        payment.setBooking(BOOKING_STUDIO_CONFIRMED);
        payment.setSessionUrl(new URL(SESSION_URL));
        payment.setSessionId(SESSION_ID);
        payment.setAmountToPay(PAYMENT_AMOUNT);
        return payment;
    }

    @SneakyThrows
    private static Payment getPaymentExpired() {
        Payment payment = new Payment();
        payment.setId(DEFAULT_ID_ONE);
        payment.setStatus(PAYMENT_STATUS_EXPIRED);
        payment.setBooking(BOOKING_STUDIO_PENDING);
        payment.setSessionUrl(new URL(SESSION_URL));
        payment.setSessionId(SESSION_ID);
        payment.setAmountToPay(PAYMENT_AMOUNT);
        return payment;
    }

    private static PaymentResponse getPaymentPendingResponse() {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(DEFAULT_ID_ONE);
        paymentResponse.setPaymentStatus(PAYMENT_STATUS_PAID.name());
        paymentResponse.setBookingId(String.valueOf(DEFAULT_ID_ONE));
        paymentResponse.setSessionUrl(SESSION_URL);
        paymentResponse.setSessionId(SESSION_ID);
        paymentResponse.setAmount(String.valueOf(PAYMENT_AMOUNT));
        return paymentResponse;
    }

    private static PaymentResponse getRenewedPaymentPendingResponse() {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(DEFAULT_ID_THREE);
        paymentResponse.setPaymentStatus(PAYMENT_STATUS_PENDING.name());
        paymentResponse.setBookingId(String.valueOf(DEFAULT_ID_ONE));
        paymentResponse.setSessionUrl(SESSION_URL);
        paymentResponse.setSessionId(SESSION_ID);
        paymentResponse.setAmount(String.valueOf(PAYMENT_AMOUNT));
        return paymentResponse;
    }

    private static PaymentResponse getSecondPaymentPendingResponse() {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(DEFAULT_ID_TWO);
        paymentResponse.setPaymentStatus(PAYMENT_STATUS_PENDING.name());
        paymentResponse.setBookingId(String.valueOf(DEFAULT_ID_TWO));
        paymentResponse.setSessionUrl(SESSION_URL);
        paymentResponse.setSessionId(SESSION_ID);
        paymentResponse.setAmount(String.valueOf(PAYMENT_AMOUNT));
        return paymentResponse;
    }

    private static PaymentResponse getPaymentPaidResponse() {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(DEFAULT_ID_ONE);
        paymentResponse.setPaymentStatus(PAYMENT_STATUS_PAID.name());
        paymentResponse.setBookingId(String.valueOf(DEFAULT_ID_ONE));
        paymentResponse.setSessionUrl(SESSION_URL);
        paymentResponse.setSessionId(SESSION_ID);
        paymentResponse.setAmount(String.valueOf(PAYMENT_AMOUNT));
        return paymentResponse;
    }

    private static TelegramChat getSubscribedTelegramChat() {
        TelegramChat telegramChat = new TelegramChat();
        telegramChat.setChatId(DEFAULT_ID_SEVEN);
        telegramChat.setUser(USER_CUSTOMER);
        telegramChat.setSubscribed(true);
        return telegramChat;
    }

    private static TelegramChat getUnsubscribedTelegramChat() {
        TelegramChat telegramChat = new TelegramChat();
        telegramChat.setChatId(DEFAULT_ID_SEVEN);
        telegramChat.setUser(USER_CUSTOMER);
        telegramChat.setSubscribed(false);
        return telegramChat;
    }
}

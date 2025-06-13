package bookingapp.mapper;

import bookingapp.config.MapperConfig;
import bookingapp.dto.accommodation.AccommodationDto;
import bookingapp.dto.accommodation.AccommodationNotificationDto;
import bookingapp.dto.accommodation.AccommodationRequestDto;
import bookingapp.model.accommodation.Accommodation;
import bookingapp.model.accommodation.Address;
import bookingapp.model.accommodation.AmenityType;
import bookingapp.telegram.NotificationTemplates;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = AddressMapper.class)
public interface AccommodationMapper {
    String AMENITIES_DELIMITER = ", ";

    @Mapping(target = "type", source = "type")
    @Mapping(target = "location", source = "location.id")
    @Mapping(target = "amenityIds", ignore = true)
    AccommodationDto toDto(Accommodation accommodation);

    List<AccommodationDto> toDto(Iterable<Accommodation> accommodations);

    @AfterMapping
    default void setAmenityIds(
            @MappingTarget AccommodationDto accommodationDto,
            Accommodation accommodation
    ) {
        Set<Long> ids = accommodation.getAmenities().stream()
                .map(AmenityType::getId)
                .collect(Collectors.toSet());
        accommodationDto.setAmenityIds(ids);
    }

    @Mapping(source = "accommodationType", target = "type")
    @Mapping(source = "address", target = "location")
    @Mapping(target = "amenities", ignore = true)
    Accommodation toModel(AccommodationRequestDto requestDto);

    @Mapping(target = "type", source = "accommodationType")
    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "location", source = "address")
    void updateModel(@MappingTarget Accommodation accommodation,
                     AccommodationRequestDto requestDto);

    @Mapping(target = "accommodationTypeName", source = "accommodation.type")
    @Mapping(target = "accommodationSize", source = "accommodation.size")
    @Mapping(target = "amenities", source = "accommodation.amenities",
            qualifiedByName = "amenitiesAsString")
    @Mapping(target = "location", source = "accommodation.location",
            qualifiedByName = "addressAsString")
    AccommodationNotificationDto toNotificationDto(Accommodation accommodation);

    @Named("amenitiesAsString")
    default String getAmenitiesString(Set<AmenityType> amenities) {
        return amenities.stream()
                .map(e -> e.getName().name())
                .collect(Collectors.joining(AMENITIES_DELIMITER));
    }

    @Named("addressAsString")
    default String getAddressString(Address address) {
        return String.format(NotificationTemplates.NOTIFICATION_ADDRESS_TEMPLATE,
                address.getAddress(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry());
    }
}

package bookingapp.mapper.accommodation;

import bookingapp.config.MapperConfig;
import bookingapp.dto.address.AddressRequestDto;
import bookingapp.model.accommodation.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface AddressMapper {
    @Mapping(source = "zip", target = "zipCode")
    Address toModel(AddressRequestDto requestDto);

    @Mapping(source = "zip", target = "zipCode")
    void updateModel(@MappingTarget Address address, AddressRequestDto requestDto);
}

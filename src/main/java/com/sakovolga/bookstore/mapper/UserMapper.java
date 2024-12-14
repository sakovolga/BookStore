package com.sakovolga.bookstore.mapper;

import com.sakovolga.bookstore.dto.UserDto;
import com.sakovolga.bookstore.entity.User;
import com.sakovolga.bookstore.entity.enums.Role;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mappings(
            @Mapping(target = "id", source = "userId")
    )
    UserDto toDto(User user);

    @AfterMapping
    default void generateUserDto(@MappingTarget UserDto userDto, User user) {
        userDto.setPassword("*****");
    }

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "secondName", source = "secondName")
    @Mapping(target = "password", source = "password")
    User toEntity(UserDto userDto);

    @AfterMapping
    default void generateUser(@MappingTarget User user, UserDto userDto) {
        user.setRoles(List.of(Role.CUSTOMER));
        user.setCreatedAt(LocalDateTime.now());
    }
}

package com.adriannebulao.tasktracker.user.presentation;

import com.adriannebulao.tasktracker.user.domain.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserRequestDto userRequestDto, @MappingTarget User user);
}

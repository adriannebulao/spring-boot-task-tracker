package com.adriannebulao.tasktracker.userprofile.presentation;

import com.adriannebulao.tasktracker.userprofile.domain.UserProfile;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(target = "tasks", ignore = true)
    UserProfile toEntity(UserProfileRequestDto userRequestDto);

    UserProfileResponseDto toDto(UserProfile user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "tasks", ignore = true)
    void updateUserProfileFromDto(UserProfileRequestDto userRequestDto, @MappingTarget UserProfile user);
}

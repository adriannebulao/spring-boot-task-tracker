package com.adriannebulao.tasktracker.task;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
interface TaskMapper {
    Task toEntity(TaskRequestDto taskRequestDto);

    TaskResponseDto toDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromDto(TaskRequestDto taskRequestDto, @MappingTarget Task task);
}

package com.adriannebulao.tasktracker.userprofile.presentation;

import com.adriannebulao.tasktracker.security.presentation.UserAccountSummaryDto;
import com.adriannebulao.tasktracker.task.presentation.TaskSummaryDto;

import java.util.List;

public record UserProfileResponseDto(
        Long id,
        String firstName,
        String lastName,
        String imageUrl,
        UserAccountSummaryDto userAccount,
        List<TaskSummaryDto> tasks) {}

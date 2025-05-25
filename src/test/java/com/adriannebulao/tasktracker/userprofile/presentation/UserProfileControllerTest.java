package com.adriannebulao.tasktracker.userprofile.presentation;

import com.adriannebulao.tasktracker.common.exception.UserProfileNotFoundException;
import com.adriannebulao.tasktracker.security.application.UserAccountDetailsService;
import com.adriannebulao.tasktracker.security.jwt.JwtTokenProvider;
import com.adriannebulao.tasktracker.userprofile.application.UserProfileService;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileController;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileRequestDto;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.adriannebulao.tasktracker.util.TestDataFactory.createTestUserProfileRequestDto;
import static com.adriannebulao.tasktracker.util.TestDataFactory.createTestUserProfileResponseDto;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserProfileControllerTest {

    @Autowired  private MockMvc mockMvc;

    @MockitoBean private UserProfileService userProfileService;
    @MockitoBean private JwtTokenProvider jwtTokenProvider;
    @MockitoBean private UserAccountDetailsService userAccountDetailsService;

    @Autowired private ObjectMapper objectMapper;

    @Test
    public void createUserProfile_shouldCreatedUserProfile_whenUserProfileCreated() throws Exception {
        UserProfileResponseDto userProfileResponseDto = createTestUserProfileResponseDto(1L);
        UserProfileRequestDto userProfileRequestDto = createTestUserProfileRequestDto();

        given(userProfileService.createUserProfile(ArgumentMatchers.any()))
                .willReturn(userProfileResponseDto);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName", is(userProfileRequestDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userProfileRequestDto.lastName())))
                .andExpect(jsonPath("$.imageUrl", is(userProfileRequestDto.imageUrl())))
                .andExpect(jsonPath("$.tasks").doesNotExist());
    }

    @Test
    public void getAllUserProfile_shouldReturnAllUserProfiles_WhenUserProfilesAreCreated() throws Exception {
        UserProfileResponseDto userProfileResponseDto = createTestUserProfileResponseDto(1L);
        List<UserProfileResponseDto> userProfiles = List.of(userProfileResponseDto);

        when(userProfileService.getAllUserProfiles()).thenReturn(userProfiles);

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName", is(userProfileResponseDto.firstName())))
                .andExpect(jsonPath("$[0].lastName", is(userProfileResponseDto.lastName())))
                .andExpect(jsonPath("$[0].imageUrl", is(userProfileResponseDto.imageUrl())))
                .andExpect(jsonPath("$[0].tasks").doesNotExist());
    }

    @Test
    public void getUserProfileById_ShouldReturnUserProfile_WhenUserProfileExists() throws Exception {
        UserProfileRequestDto userProfileRequestDto = createTestUserProfileRequestDto();
        UserProfileResponseDto userProfileResponseDto = createTestUserProfileResponseDto(1L);

        when(userProfileService.getUserProfileById(1L)).thenReturn(userProfileResponseDto);

        mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName", is(userProfileRequestDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userProfileRequestDto.lastName())))
                .andExpect(jsonPath("$.imageUrl", is(userProfileRequestDto.imageUrl())))
                .andExpect(jsonPath("$.tasks").doesNotExist());
    }

    @Test
    public void getUserProfileById_ShouldReturn404_WhenUserProfileNotFound() throws Exception {
        when(userProfileService.getUserProfileById(1L)).thenThrow(new UserProfileNotFoundException("User profile not found"));

        mockMvc.perform(get("/users/1").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserProfile_ShouldReturnUpdatedUserProfile_WhenUserProfileIsUpdated() throws Exception {
        Long userProfileId = 1L;
        UserProfileRequestDto userProfileRequestDto = createTestUserProfileRequestDto();
        UserProfileResponseDto userProfileResponseDto = createTestUserProfileResponseDto(userProfileId);

        when(userProfileService.updateUserProfile(userProfileId, userProfileRequestDto)).thenReturn(userProfileResponseDto);

        mockMvc.perform(put("/users/{id}", userProfileId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName", is(userProfileRequestDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userProfileRequestDto.lastName())))
                .andExpect(jsonPath("$.imageUrl", is(userProfileRequestDto.imageUrl())))
                .andExpect(jsonPath("$.tasks").doesNotExist());
    }

    @Test
    public void deleteUserProfile_ShouldReturnNoContent_WhenUserProfileIsDeleted() throws Exception {
        Long userProfileId = 1L;

        doNothing().when(userProfileService).deleteUserProfile(userProfileId);

        mockMvc.perform(delete("/users/{id}", userProfileId))
                .andExpect(status().isNoContent());

        verify(userProfileService).deleteUserProfile(userProfileId);
    }
}

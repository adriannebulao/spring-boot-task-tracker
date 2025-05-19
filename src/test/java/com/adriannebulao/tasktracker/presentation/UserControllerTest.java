package com.adriannebulao.tasktracker.presentation;

import com.adriannebulao.tasktracker.common.exception.UserNotFoundException;
import com.adriannebulao.tasktracker.user.application.UserService;
import com.adriannebulao.tasktracker.user.domain.User;
import com.adriannebulao.tasktracker.user.presentation.UserController;
import com.adriannebulao.tasktracker.user.presentation.UserRequestDto;
import com.adriannebulao.tasktracker.user.presentation.UserResponseDto;
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


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired  private MockMvc mockMvc;
    @MockitoBean private UserService userService;
    @Autowired private ObjectMapper objectMapper;

    private User createTestUser() {
        return User.builder()
                .firstName("Test")
                .lastName("Test")
                .imageUrl("Test")
                .build();
    }

    private UserRequestDto createTestUserRequestDto() {
        return new UserRequestDto(
                "Test",
                "Test",
                "Test"
        );
    }

    private UserResponseDto createTestUserResponseDto(Long id) {
        return new UserResponseDto(
                id,
                "Test",
                "Test",
                "Test",
                null
        );
    }

    @Test
    public void createUser_shouldCreatedUser_whenUserCreated() throws Exception {
        UserResponseDto mockResponse = createTestUserResponseDto(1L);
        UserRequestDto userRequestDto = createTestUserRequestDto();

        given(userService.createUser(ArgumentMatchers.any()))
                .willReturn(mockResponse);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName", is(userRequestDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userRequestDto.lastName())))
                .andExpect(jsonPath("$.imageUrl", is(userRequestDto.imageUrl())))
                .andExpect(jsonPath("$.tasks").doesNotExist());
    }

    @Test
    public void getAllUsers_shouldReturnAllUsers_WhenUsersAreCreated() throws Exception {
        UserResponseDto userResponseDto = createTestUserResponseDto(1L);
        List<UserResponseDto> users = List.of(userResponseDto);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName", is(userResponseDto.firstName())))
                .andExpect(jsonPath("$[0].lastName", is(userResponseDto.lastName())))
                .andExpect(jsonPath("$[0].imageUrl", is(userResponseDto.imageUrl())))
                .andExpect(jsonPath("$[0].tasks").doesNotExist());
    }

    @Test
    public void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
        UserRequestDto userRequestDto = createTestUserRequestDto();
        UserResponseDto userResponseDto = createTestUserResponseDto(1L);

        when(userService.getUserById(1L)).thenReturn(userResponseDto);

        mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName", is(userRequestDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userRequestDto.lastName())))
                .andExpect(jsonPath("$.imageUrl", is(userRequestDto.imageUrl())))
                .andExpect(jsonPath("$.tasks").doesNotExist());
    }

    @Test
    public void getUserById_ShouldReturn404_WhenUserNotFound() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/users/1").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateUser_ShouldReturnUpdatedUser_WhenUserIsUpdated() throws Exception {
        Long userId = 1L;
        UserRequestDto userRequestDto = createTestUserRequestDto();
        UserResponseDto userResponseDto = createTestUserResponseDto(userId);

        when(userService.updateUser(userId, userRequestDto)).thenReturn(userResponseDto);

        mockMvc.perform(put("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName", is(userRequestDto.firstName())))
                .andExpect(jsonPath("$.lastName", is(userRequestDto.lastName())))
                .andExpect(jsonPath("$.imageUrl", is(userRequestDto.imageUrl())))
                .andExpect(jsonPath("$.tasks").doesNotExist());
    }

    @Test
    public void deleteUser_ShouldReturnNoContent_WhenUserIsDeleted() throws Exception {
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(userId);
    }
}

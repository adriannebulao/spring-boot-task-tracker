package com.adriannebulao.tasktracker.user.application;

import com.adriannebulao.tasktracker.common.exception.UserNotFoundException;
import com.adriannebulao.tasktracker.user.domain.User;
import com.adriannebulao.tasktracker.user.persistence.UserRepository;
import com.adriannebulao.tasktracker.user.presentation.UserMapper;
import com.adriannebulao.tasktracker.user.presentation.UserRequestDto;
import com.adriannebulao.tasktracker.user.presentation.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @InjectMocks private UserService userService;

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
    public void createUser_ShouldReturnUserResponseDto_WhenUserIsCreated() {
        User user = createTestUser();
        UserRequestDto userRequestDto = createTestUserRequestDto();
        UserResponseDto userResponseDto = new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getImageUrl(),
                null
        );

        when(userMapper.toEntity(userRequestDto)).thenReturn(user);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userResponseDto);

        UserResponseDto savedUser = userService.createUser(userRequestDto);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.id()).isEqualTo(user.getId());
        Assertions.assertThat(savedUser.firstName()).isEqualTo(user.getFirstName());
        Assertions.assertThat(savedUser.lastName()).isEqualTo(user.getLastName());
        Assertions.assertThat(savedUser.imageUrl()).isEqualTo(user.getImageUrl());
        Assertions.assertThat(savedUser.tasks()).isNull();
    }

    @Test
    public void getAllUsers_ShouldReturnAllUsers_WhenUsersAreCreated() {
        User user1 = createTestUser();
        User user2 = createTestUser();
        List<User> users = List.of(user1, user2);

        UserResponseDto userResponseDto1 = createTestUserResponseDto(user1.getId());
        UserResponseDto userResponseDto2 = createTestUserResponseDto(user2.getId());
        List<UserResponseDto> userResponseDtos = List.of(userResponseDto1, userResponseDto2);

        when(userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(users);
        when(userMapper.toDto(user1)).thenReturn(userResponseDto1);
        when(userMapper.toDto(user2)).thenReturn(userResponseDto2);

        List<UserResponseDto> allUsers = userService.getAllUsers();

        Assertions.assertThat(allUsers).isEqualTo(userResponseDtos);
    }

    @Test
    public void getUserById_ShouldReturnUserResponseDto_WhenUserIsFound() {
        User user = createTestUser();
        UserResponseDto userResponseDto = createTestUserResponseDto(user.getId());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponseDto);

        UserResponseDto savedUser = userService.getUserById(user.getId());

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser).isEqualTo(userResponseDto);
    }

    @Test
    public void getUserById_ShouldReturnUserResponseDto_WhenUserIsNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    public void updateUser_ShouldUpdateUserResponseDto_WhenUserIsUpdated() {
        User user = createTestUser();
        UserRequestDto userRequestDto = createTestUserRequestDto();

        User updatedUser = User.builder()
                .id(user.getId())
                .firstName("Updated")
                .lastName("Updated")
                .imageUrl("Updated")
                .build();

        UserResponseDto userResponseDto = new UserResponseDto(
                updatedUser.getId(),
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getImageUrl(),
                null
        );

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(updatedUser);
        when(userMapper.toDto(updatedUser)).thenReturn(userResponseDto);

        UserResponseDto updatedUserDto = userService.updateUser(user.getId(), userRequestDto);

        Assertions.assertThat(updatedUserDto).isNotNull();
        Assertions.assertThat(updatedUserDto).isEqualTo(userResponseDto);

        Mockito.verify(userMapper).updateUserFromDto(userRequestDto, user);
    }

    @Test
    public void deleteUser_ShouldDeleteUserResponseDto_WhenUserIsDeleted() {
        User user = createTestUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());

        Mockito.verify(userRepository).delete(user);
    }
}

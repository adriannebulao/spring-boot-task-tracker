package com.adriannebulao.tasktracker.user.application;

import com.adriannebulao.tasktracker.user.domain.User;
import com.adriannebulao.tasktracker.user.domain.UserNotFoundException;
import com.adriannebulao.tasktracker.user.persistence.UserRepository;
import com.adriannebulao.tasktracker.user.presentation.UserMapper;
import com.adriannebulao.tasktracker.user.presentation.UserRequestDto;
import com.adriannebulao.tasktracker.user.presentation.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC,"id"))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userMapper.updateUserFromDto(userRequestDto, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
}

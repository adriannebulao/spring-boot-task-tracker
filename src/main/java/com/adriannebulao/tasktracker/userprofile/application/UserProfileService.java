package com.adriannebulao.tasktracker.userprofile.application;

import com.adriannebulao.tasktracker.s3.S3Service;
import com.adriannebulao.tasktracker.security.domain.UserAccount;
import com.adriannebulao.tasktracker.security.persistence.UserAccountRepository;
import com.adriannebulao.tasktracker.userprofile.domain.UserAccountNotFoundException;
import com.adriannebulao.tasktracker.userprofile.domain.UserProfile;
import com.adriannebulao.tasktracker.common.exception.UserProfileNotFoundException;
import com.adriannebulao.tasktracker.userprofile.persistence.UserProfileRepository;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileMapper;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileRequestDto;
import com.adriannebulao.tasktracker.userprofile.presentation.UserProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserProfileMapper userProfileMapper;
    private final S3Service s3Service;

    public UserProfileResponseDto createUserProfile(
            UserProfileRequestDto userProfileRequestDto,
            MultipartFile imageFile) {
        UserAccount userAccount = userAccountRepository
                .findById(userProfileRequestDto.accountId())
                .orElseThrow(() -> new UserAccountNotFoundException("User account not found"));

        String imageUrl = s3Service.uploadFile(imageFile);

        UserProfile userProfile = userProfileMapper.toEntity(userProfileRequestDto);
        userProfile.setImageUrl(imageUrl);
        userProfile.setUserAccount(userAccount);

        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toDto(userProfile);
    }

    public List<UserProfileResponseDto> getAllUserProfiles() {
        return userProfileRepository.findAll(Sort.by(Sort.Direction.ASC,"id"))
                .stream()
                .peek(userProfile -> {
                    String presignedUrl = s3Service.generatePresignedUrl(userProfile.getImageUrl());
                    userProfile.setImageUrl(presignedUrl);
                })
                .map(userProfileMapper::toDto)
                .toList();
    }

    public UserProfileResponseDto getUserProfileById(Long id) {
        UserProfile userProfile = userProfileRepository
                .findById(id)
                .orElseThrow(() -> new UserProfileNotFoundException("User profile not found"));

        String presignedUrl = s3Service.generatePresignedUrl(userProfile.getImageUrl());
        userProfile.setImageUrl(presignedUrl);

        return userProfileMapper.toDto(userProfile);
    }

    public UserProfileResponseDto updateUserProfile(
            Long id,
            UserProfileRequestDto userProfileRequestDto,
            MultipartFile imageFile) {
        UserProfile userProfile = userProfileRepository
                .findById(id)
                .orElseThrow(() -> new UserProfileNotFoundException("User profile not found"));

        userProfileMapper.updateUserProfileFromDto(userProfileRequestDto, userProfile);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = s3Service.uploadFile(imageFile);
            userProfile.setImageUrl(imageUrl);
        }

        UserAccount userAccount = userAccountRepository
                .findById(userProfileRequestDto.accountId())
                .orElseThrow(() -> new UserAccountNotFoundException("User account not found"));

        userProfile.setUserAccount(userAccount);
        UserProfile updatedUser = userProfileRepository.save(userProfile);
        return userProfileMapper.toDto(updatedUser);
    }

    public void deleteUserProfile(Long id) {
        UserProfile userProfile = userProfileRepository
                .findById(id)
                .orElseThrow(() -> new UserProfileNotFoundException("User profile not found"));
        userProfileRepository.delete(userProfile);
    }
}

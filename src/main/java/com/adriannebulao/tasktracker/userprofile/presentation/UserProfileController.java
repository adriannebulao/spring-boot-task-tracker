package com.adriannebulao.tasktracker.userprofile.presentation;

import com.adriannebulao.tasktracker.userprofile.application.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {

    public final UserProfileService userProfileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileResponseDto> createUserProfile(
            @RequestPart("user") @Valid UserProfileRequestDto userRequestDto,
            @RequestPart("image") MultipartFile imageFile) {
        UserProfileResponseDto userProfile = userProfileService.createUserProfile(userRequestDto, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfile);
    }

    @GetMapping
    public ResponseEntity<List<UserProfileResponseDto>> getAllUserProfiles() {
        List<UserProfileResponseDto> userProfiles = userProfileService.getAllUserProfiles();
        return ResponseEntity.ok(userProfiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> getUserProfileById(@PathVariable Long id) {
        UserProfileResponseDto userProfile = userProfileService.getUserProfileById(id);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(
            @PathVariable Long id,
            @RequestPart("user") @Valid UserProfileRequestDto userProfileRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        UserProfileResponseDto userProfile = userProfileService.updateUserProfile(id, userProfileRequestDto, imageFile);
        return ResponseEntity.ok(userProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }
}

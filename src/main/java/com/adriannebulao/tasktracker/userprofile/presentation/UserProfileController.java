package com.adriannebulao.tasktracker.userprofile.presentation;

import com.adriannebulao.tasktracker.userprofile.application.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {

    public final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileResponseDto> createUserProfile(@RequestBody UserProfileRequestDto userRequestDto) {
        UserProfileResponseDto userProfile = userProfileService.createUserProfile(userRequestDto);
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

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(
            @PathVariable Long id,
            @RequestBody UserProfileRequestDto userProfileRequestDto) {
        UserProfileResponseDto userProfile = userProfileService.updateUserProfile(id, userProfileRequestDto);
        return ResponseEntity.ok(userProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }
}

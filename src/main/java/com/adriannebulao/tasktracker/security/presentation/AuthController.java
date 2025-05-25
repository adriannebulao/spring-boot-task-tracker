package com.adriannebulao.tasktracker.security.presentation;

import com.adriannebulao.tasktracker.security.domain.Role;
import com.adriannebulao.tasktracker.security.domain.UserAccount;
import com.adriannebulao.tasktracker.security.jwt.JwtTokenProvider;
import com.adriannebulao.tasktracker.security.persistence.RoleRepository;
import com.adriannebulao.tasktracker.security.persistence.UserAccountRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        if (userAccountRepository.existsByUsername(registerRequestDto.username())) {
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponseDto(null, null, null, "Username is taken!")
            );
        }

        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Default role USER not found"));

        UserAccount userAccount = UserAccount.builder()
                .username(registerRequestDto.username())
                .password(passwordEncoder.encode(registerRequestDto.password()))
                .roles(Collections.singleton(role))
                .build();

        userAccountRepository.save(userAccount);

        Set<String> roles = userAccount.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        RegisterResponseDto registerResponseDto = new RegisterResponseDto(
                userAccount.getId(),
                userAccount.getUsername(),
                roles,
                "Successfully registered user!"
        );

        return ResponseEntity.ok(registerResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtTokenProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(new AuthResponseDto(jwtToken, "Bearer"));
    }
}

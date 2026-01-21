package com.personal.coffee_catalog.service;

import com.personal.coffee_catalog.model.User;
import com.personal.coffee_catalog.repository.UserRepository;
import com.personal.coffee_catalog.request.LoginRequest;
import com.personal.coffee_catalog.request.RegisterRequest;
import com.personal.coffee_catalog.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthResponse register(RegisterRequest request) {
    // Check if user already exists
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new IllegalArgumentException("Email already registered");
    }

    // Create new user
    var user = User.builder()
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .firstName(request.getFirstName())
      .lastName(request.getLastName())
      .role(request.getRole())
      .build();

    userRepository.save(user);

    // Generate JWT token
    var jwtToken = jwtService.generateToken(user);

    return AuthResponse.builder()
      .accessToken(jwtToken)
      .email(user.getEmail())
      .role(user.getRole().name())
      .build();
  }

  @Transactional(timeout = 5)
  public AuthResponse login(LoginRequest request) {
    // Authenticate user
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          request.getEmail(),
          request.getPassword()
        )
      );
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid email or password");
    }

    // Get user from database
    var user = userRepository.findByEmail(request.getEmail())
      .orElseThrow(() -> new RuntimeException("User not found"));

    // Generate JWT token
    var jwtToken = jwtService.generateToken(user);

    return AuthResponse.builder()
      .accessToken(jwtToken)
      .email(user.getEmail())
      .role(user.getRole().name())
      .build();
  }
}

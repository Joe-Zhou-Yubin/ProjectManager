package com.projmanager.manager.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.projmanager.manager.models.ERole;
import com.projmanager.manager.models.Role;
import com.projmanager.manager.models.User;
import com.projmanager.manager.payload.request.LoginRequest;
import com.projmanager.manager.payload.request.SignupRequest;
import com.projmanager.manager.payload.response.JwtResponse;
import com.projmanager.manager.payload.response.MessageResponse;
import com.projmanager.manager.repository.RoleRepository;
import com.projmanager.manager.repository.UserRepository;
import com.projmanager.manager.security.jwt.JwtUtils;
import com.projmanager.manager.security.services.UserDetailsImpl;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
      System.out.println("Received signup request for username: " + signUpRequest.getUsername());

      if (userRepository.existsByUsername(signUpRequest.getUsername())) {
          System.out.println("Username is already taken.");
          return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
      }

      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
          System.out.println("Email is already in use.");
          return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
      }

      // Create new user's account
      User user = new User(signUpRequest.getUsername(), 
                 signUpRequest.getEmail(),
                 encoder.encode(signUpRequest.getPassword()));

      Set<String> strRoles = signUpRequest.getRole();
      Set<Role> roles = new HashSet<>();

      if (strRoles == null) {
          System.out.println("No roles specified, assigning default role.");
          Role userRole = roleRepository.findByName(ERole.ROLE_MEMBER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
      } else {
          System.out.println("Assigning roles: " + strRoles);
          strRoles.forEach(role -> {
              switch (role) {
                  case "manager":
                      Role adminRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(adminRole);

                      break;
                  case "leader":
                      Role modRole = roleRepository.findByName(ERole.ROLE_LEADER)
                          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(modRole);

                      break;
                  default:
                      Role userRole = roleRepository.findByName(ERole.ROLE_MEMBER)
                          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(userRole);
              }
          });
      }

      user.setRoles(roles);
      userRepository.save(user);

      System.out.println("User registered successfully!");
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  
  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userRepository.findAll();
    return ResponseEntity.ok(users);
  }
  
  @DeleteMapping("/delete/{userId}")
  public ResponseEntity<?> deleteUserByUserId(@PathVariable String userId) {
    Optional<User> userOptional = userRepository.findByUserId(userId);

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      userRepository.delete(user);
      return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
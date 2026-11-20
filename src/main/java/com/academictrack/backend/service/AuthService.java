package com.academictrack.backend.service;

import com.academictrack.backend.dto.LoginRequestDTO;
import com.academictrack.backend.dto.SignupRequestDTO;
import com.academictrack.backend.entity.Role;
import com.academictrack.backend.entity.User;
import com.academictrack.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtService jwtService)
    {
        this.userRepository=userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String signup(SignupRequestDTO request){
        //check email already exists
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole(Role.User);

        userRepository.save(user);

        return "User Registered Successfully";

    }

    public String login(LoginRequestDTO request) {

        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check password
        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid password");
        }

        // 3. Generate JWT token
        return jwtService.generateToken(user.getEmail());
    }

}

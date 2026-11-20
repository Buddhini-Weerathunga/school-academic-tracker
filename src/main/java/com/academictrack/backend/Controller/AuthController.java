package com.academictrack.backend.Controller;


import com.academictrack.backend.dto.LoginRequestDTO;
import com.academictrack.backend.dto.SignupRequestDTO;
import com.academictrack.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController (AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody SignupRequestDTO request ){
        String response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String>login(
            @Valid @RequestBody LoginRequestDTO request){
        String token = authService.login(request);

        return ResponseEntity.ok(token);

    }


}

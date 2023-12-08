package AunthenticationService.service;

import AunthenticationService.domain.AuthRequest;
import AunthenticationService.domain.AuthResponse;
import AunthenticationService.domain.RegisterRequest;
import AunthenticationService.repository.UserCredentialRepository;
import AunthenticationService.util.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    @Override
    public AuthResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        return null;
    }
}

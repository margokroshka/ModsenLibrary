package AunthenticationService.service;

import AunthenticationService.domain.AuthRequest;
import AunthenticationService.domain.AuthResponse;
import AunthenticationService.domain.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
}

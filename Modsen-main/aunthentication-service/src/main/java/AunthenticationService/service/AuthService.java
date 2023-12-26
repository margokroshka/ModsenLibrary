package AunthenticationService.service;

import AunthenticationService.domain.AuthRequest;
import AunthenticationService.domain.AuthResponse;
import AunthenticationService.domain.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request) throws Exception;
    AuthResponse authenticate(AuthRequest request) throws Exception;
}

package AunthenticationService.service;

import AunthenticationService.domain.AuthRequest;
import AunthenticationService.domain.AuthResponse;
import AunthenticationService.domain.RegisterRequest;
import AunthenticationService.domain.UserCredential;
import AunthenticationService.repository.UserCredentialRepository;
import AunthenticationService.util.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public AuthResponse register(RegisterRequest request) throws Exception {
        if (userCredentialRepository.findByEmail(request.email()).isEmpty()) {
            UserCredential user = UserCredential.builder()
                    .login(request.name())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .build();
            userCredentialRepository.save(user);
            String jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new Exception(
                    String.format("user not found" +
                            request.email())
            );
        }
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        UserCredential user = userCredentialRepository.findByEmail(request.email())
                .orElseThrow(() -> new Exception(
                                String.format("user not found" +
                                        request.email())
                        )
                );
        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }


}

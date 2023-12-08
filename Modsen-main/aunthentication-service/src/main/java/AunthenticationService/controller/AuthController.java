package AunthenticationService.controller;

import AunthenticationService.domain.AuthRequest;
import AunthenticationService.domain.AuthResponse;
import AunthenticationService.domain.RegisterRequest;
import AunthenticationService.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    @Operation(
            summary = "Registration of a new User",
            description = "Registration of a new user with the entered information.",
            tags = { "authentication" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The user has been successfully registered",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/registration")
    public ResponseEntity<AuthResponse> registrationUser(@RequestBody RegisterRequest registrationUser) {
        return ResponseEntity
                .ok()
                .body(authenticationService.register(registrationUser));
    }

    @Operation(
            summary = "User Authentication",
            description = "Authentication the user with the entered information.",
            tags = { "tutorials", "get" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful authentication",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Authentication failed", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Access forbidden", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema()))
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity
                .ok()
                .body(authenticationService.authenticate(request));
    }
}

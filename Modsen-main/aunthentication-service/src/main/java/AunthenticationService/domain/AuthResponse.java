package AunthenticationService.domain;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {
}

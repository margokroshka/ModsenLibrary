package AunthenticationService.domain;

import lombok.Builder;

@Builder
public record AuthRequest(String email, String password) {
}

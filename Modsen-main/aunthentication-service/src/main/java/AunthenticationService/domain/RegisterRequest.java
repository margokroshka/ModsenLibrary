package AunthenticationService.domain;

import lombok.Builder;

@Builder
public record RegisterRequest(String name, String email, String password) {
}

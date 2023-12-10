package gateway.service.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import gateway.service.JWTService;

import java.util.Objects;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private static final int JWT_START_INDEX_IN_TOKEN = 7;
    private static final String TOKEN_PREFIX = "Bearer ";
    private final PathValid pathValid;
    private final JWTService jwtService;

    public AuthFilter(PathValid routeValidator, JWTService jwtService) {
        super(Config.class);
        this.pathValid = routeValidator;
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (pathValid.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String token = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (token != null && token.startsWith(TOKEN_PREFIX)) {
                    token = token.substring(JWT_START_INDEX_IN_TOKEN);
                }
                try {
                    jwtService.validateToken(token);
                } catch (Exception e) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config {

    }
}

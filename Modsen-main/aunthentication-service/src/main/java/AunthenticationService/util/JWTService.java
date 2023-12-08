package AunthenticationService.util;

import AunthenticationService.domain.UserCredential;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTService {
    @Value("${jwt.secret}")
    public String SECRET;

    public String generateToken(UserCredential userCredential){
        Map<String, Object> claims =new HashMap<>();
        return createToken(claims, userCredential);
    }


    public String createToken( Map<String, Object> claims, UserCredential userCredential){

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userCredential.getEmail())
                .setSubject(userCredential.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}

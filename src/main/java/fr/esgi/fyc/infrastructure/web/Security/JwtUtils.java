package fr.esgi.fyc.infrastructure.web.Security;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;

import java.util.Date;

@Service
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Autowired
    UserService userService;

    private String createToken(Claims claims, Long id){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(id))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Claims generateClaims(User user){
        Claims claims = Jwts.claims().setSubject(String.valueOf(user.getId()));
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        return claims;
    }

    public String generateToken(User user){
        Claims claims = generateClaims(user);
        return createToken(claims, Long.valueOf(user.getId()));
    }

}

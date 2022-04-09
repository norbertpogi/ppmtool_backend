package com.ppmtool.ppmtool.security;

import com.ppmtool.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ppmtool.ppmtool.security.SecurityConstants.SECRET_KEY;
import static com.ppmtool.ppmtool.security.SecurityConstants.TOKEN_EXPIRATION_TIME;

@Component
public class JwtTokenProvider {
    //Generate the token
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);
        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now).
                setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    //validate the token
    public boolean validToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch (SignatureException sigEx) {
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException mEx) {
            System.out.println("Invalid JWT Token " + mEx);
        }catch (ExpiredJwtException exPired) {
            System.out.println("Invalid JWT token");
        }catch (UnsupportedJwtException unspex) {
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException illex) {
            System.out.println("IllegalArgument Token: JWT claims string empty" );
        }
        return false;
    }

    //get user id from token
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");
        return Long.parseLong(id);
    }
}

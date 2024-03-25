package com.project.JewelHub.config;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import com.project.JewelHub.user.User;
import com.project.JewelHub.user.dtos.UserResDto;
import com.project.JewelHub.util.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${token.expire}")
    private String EXPIRE_TOKEN;

    public static String extractToken(HttpServletRequest request, String key) {
        try {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        } catch (NullPointerException ex) {
            return  null;
        }
        return null;
    }

    public boolean validateToken(String token) {
        if (token == null || isTokenExpired(token)){
            throw new CustomException("Invalid or Missing token", 403);
        }else {
            return  true;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            return decodeToken(token).getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (Exception ex) {
            return true;
        }
    }

    public String generateToken(UserResDto user) {
        System.out.println(user.getUserId());
        Date currentDate = new Date();
        Calendar calender = Calendar.getInstance();
        calender.setTime(currentDate);
        calender.add(Calendar.DAY_OF_MONTH, Integer.parseInt(EXPIRE_TOKEN));
        Date updatedDate = calender.getTime();

        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject("Token")
                .setIssuedAt(currentDate)
                .setExpiration(updatedDate)
                .signWith(key)
                .claim("user", user)
                /*.claim("userId", user.getUserId())
                .claim("firstName", user.getFirstname())
                .claim("lastName", user.getLastname())
                .claim("email", user.getEmail())
                .claim("contact", user.getContact())
                .claim("role", user.getRole())*/
                .compact();
    }


    public Claims decodeToken(String token) {
        try {
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw new CustomException("Token Expired", 404);
        } catch (Exception ex){
            throw new CustomException("Token Invalid", 404);
        }
    }

    static void validateAuthority(User user, HttpServletRequest request) {
        if (request.getServletPath().contains("/api/admin/") && !user.getRole().equals("Admin")) {
            throw new CustomException("User not Authorized !", 400);
        }
    }

    public static User claimsToUser(Claims claims) {
        Map<String, Object> userMap = claims.get("user", Map.class);
        User user = new User();
        user.setUserId((Integer) userMap.get("userId"));
        user.setFirstname((String) userMap.get("firstname"));
        user.setLastname((String) userMap.get("lastname"));
        user.setContact((Long) userMap.get("contact"));
        user.setEmail((String) userMap.get("email"));
        user.setRole((String) userMap.get("role"));
        return user;
    }
}

package com.project.JewelHub.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.JewelHub.user.User;
import com.project.JewelHub.util.ApiResponse;
import com.project.JewelHub.util.CustomException;
import com.project.JewelHub.util.CustomMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomMapper.CustomUserDetailService customUserDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (
                request.getServletPath().contains("/api/auth/") ||
                        request.getServletPath().contains("/itemImage/") ||
                        request.getServletPath().contains("/video/") ||
                        request.getServletPath().contains("/store/")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = jwtService.extractToken(request, "token");
            jwtService.validateToken(jwtToken);
            Claims claims = jwtService.decodeToken(jwtToken);
            User user = jwtService.claimsToUser(claims);
            jwtService.validateAuthority(user, request);
//             Continue with the filter chain
            request.setAttribute("user", user);
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
//            System.out.println("Custom Error: " + e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            ApiResponse apiResponse = new ApiResponse(false, null, e.getMessage());
            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        }
    }
    /*@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/auth/") || request.getServletPath().contains("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = JwtService.extractToken(request, "auth");
        boolean valid = jwtService.validateToken(token);
        if (!valid) {
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseWrapper responseWrapper = new ResponseWrapper(false, 401, "Invalid or Missing token", null);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(responseWrapper));
        } else {
            System.out.println(token);
            Claims claims = jwtService.decodeToken(token);
            int userId = claims.get("userId", Integer.class);
            String firstName = claims.get("firstName", String.class);
            String lastName = claims.get("lastName", String.class);
            String email = claims.get("email", String.class);
            Long contact = claims.get("contact", Long.class);
            String role = claims.get("role", String.class);


            if (request.getServletPath().contains("/api/admin")){
                ObjectMapper objectMapper = new ObjectMapper();
                ResponseWrapper responseWrapper = new ResponseWrapper(false, 401, "Not authorized", null);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(responseWrapper));
            } else {
                request.setAttribute("userId", userId);
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("email", email);
                request.setAttribute("contact", contact);
                request.setAttribute("role", role);

                filterChain.doFilter(request, response);
            }
        }
    }*/
}
package com.project.JewelHub.config;

import com.project.JewelHub.util.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private CustomMapper.CustomUserDetailService customUserDetailService;

    private final JwtFilter jwtFilter;

    private final CustomAuthEntryPoint entryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint).accessDeniedHandler(accessDeniedHandler))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(corsFilter()))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("Admin")
                        /*.requestMatchers("/api/admin/**").authenticated()*/
                        .anyRequest().authenticated());
//                      .requestMatchers(HttpMethod.GET,"/").hasAnyRole("user","admin")
//                      .requestMatchers(HttpMethod.GET,"/allUsers").hasAnyRole("user","admin")
//                      .requestMatchers(HttpMethod.GET,"/addUser").hasRole("admin")
//                      .requestMatchers(HttpMethod.POST,"/save").hasRole("admin")
//                      .requestMatchers(HttpMethod.GET,"/update/**").hasRole("admin")
//                      .requestMatchers(HttpMethod.PUT,"/update/**").hasRole("admin")
//                      .requestMatchers(HttpMethod.GET,"/deleteUser/**").hasRole("admin")

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}

package kr.nyamnyam_kr.config;

import kr.nyamnyam_kr.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserDetailsServiceImpl userDetailsService) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/user/**", "/login", "/user/join").permitAll()
                                .anyRequest().authenticated())
                .formLogin((form) ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/user/auth")
                                .successForwardUrl("/user/authOk")
                                .failureForwardUrl("/user/authFail"))
                .logout((logout) ->
                        logout
                                .logoutUrl("/user/logOut")
                                .logoutSuccessUrl("/user/logOutSuccess")
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID"))
                .userDetailsService(userDetailsService)
                .cors(c -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", corsConfiguration);
                    c.configurationSource(source);
                });

        return httpSecurity.build();
    }
}

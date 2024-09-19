//package kr.nyamnyam.config;
//
//import kr.nyamnyam.service.impl.UserDetailsServiceImpl;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserDetailsServiceImpl userDetailsService) throws Exception {
//        httpSecurity
//                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/user/**", "/").permitAll()
//                        .anyRequest().authenticated())
//                .formLogin(form -> form
//                        .loginProcessingUrl("/user/auth")
//                        .successForwardUrl("/user/authOk")
//                        .failureForwardUrl("/user/authFail"))
//                .logout(logout -> logout
//                        .logoutUrl("/user/logOut")
//                        .logoutSuccessUrl("/user/logOutSuccess")
//                        .clearAuthentication(true)
//                        .deleteCookies("JSESSIONID"))
//                .userDetailsService(userDetailsService)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedOrigin("http://localhost:3000");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}

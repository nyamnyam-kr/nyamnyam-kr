//package kr.nyamnyam.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.config.CorsRegistry;
//import org.springframework.web.reactive.config.EnableWebFlux;
//import org.springframework.web.reactive.config.ResourceHandlerRegistry;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//
//@Configuration
//@EnableWebFlux
//public class WebConfig implements WebFluxConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("http://localhost:3000") // 허용할 클라이언트의 주소
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH")
//                .allowedHeaders("*")
//                .exposedHeaders("*")
//                .allowCredentials(true); // withCredentials 설정을 true로
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("classpath:/static/uploads/posts");
//    }
//}

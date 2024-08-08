package com.example.scrabble.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for security settings.
 */
@Configuration
public class SecurityConfig {

  /**
   * Configures the security filter chain.
   *
   * @param http the HttpSecurity to modify
   * @return the configured SecurityFilterChain
   * @throws Exception if an error occurs while configuring the security filter chain
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(httpSecurityCorsConfigurer ->
      httpSecurityCorsConfigurer.configurationSource(request -> {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
      })
    ).csrf(csrf -> csrf.ignoringRequestMatchers("/**"));
    return http.build();
  }

  /**
   * Configures CORS settings.
   *
   * @return the configured WebMvcConfigurer
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
          @Override
          public void addCorsMappings(@NonNull CorsRegistry registry) {
              registry.addMapping("/**")
                      .allowedOrigins("http://localhost:3000")
                      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                      .allowedHeaders("*")
                      .allowCredentials(true);
          }
      };
  }
}
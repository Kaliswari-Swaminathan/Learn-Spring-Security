package com.demo.pkce;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth->
                auth.anyRequest().authenticated()).oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()));
        http.cors(cors ->cors.configurationSource(configurationSource()));
        return  http.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource(){
        CorsConfiguration source=new CorsConfiguration();
        source.setAllowedOrigins(List.of("http://localhost:5173/"));
        source.setAllowedMethods(Arrays.asList("GET","POST"));
        source.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        source.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource=new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/api/**", source);
        return urlBasedCorsConfigurationSource;



    }
}

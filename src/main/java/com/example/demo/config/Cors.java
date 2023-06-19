package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class Cors implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Altere para a origem (domínio) do seu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Especifique os métodos HTTP permitidos
                .allowedHeaders("Content-Type", "Authorization") // Especifique os cabeçalhos permitidos
                .allowCredentials(true);
    }
}


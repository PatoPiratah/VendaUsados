package com.example.demo.config;

import com.example.demo.security.JwtAuthFilter;
import com.example.demo.security.JwtService;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/clientesPF/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/clientesPF/**")
                .hasAnyRole( "ADMIN")

                .antMatchers(HttpMethod.POST,"/api/v1/clientesPJ/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/clientesPJ/**")
                .hasAnyRole( "ADMIN")

                .antMatchers("/api/v1/compras/**")
                .hasAnyRole( "ADMIN")

                .antMatchers("/api/v1/funcionarios/**")
                .hasAnyRole( "ADMIN")

                .antMatchers("/api/v1/lojas/**")
                .hasAnyRole( "ADMIN")

                .antMatchers("/api/v1/tiposVeiculo/**")
                .hasAnyRole( "ADMIN")

                .antMatchers("/api/v1/vendas/**")
                .hasAnyRole( "ADMIN")

                .antMatchers("/api/v1/veiculos/**")
                .hasAnyRole( "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/veiculos/**")
                .permitAll()

                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/api/v1/veiculos/**"
        );
    }
}
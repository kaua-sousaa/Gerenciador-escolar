package kaua.sistema_gerenciamento_escolar.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests( auth -> auth
            .requestMatchers("/login").permitAll()
            .requestMatchers("/administrador/**").hasRole("ADMIN")
            .requestMatchers("/aluno/**").hasRole("ALUNO")
            .requestMatchers("/professor/**").hasRole("PROFESSOR")
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
                    .successHandler((request, response, authentication) -> {
                        String role = authentication.getAuthorities().iterator().next().getAuthority();
                        switch (role) {
                            case "ROLE_ADMIN":
                                response.sendRedirect("/administrador");break;
                            case "ROLE_ALUNO":
                                response.sendRedirect("/aluno");break;
                            case "ROLE_PROFESSOR":
                                response.sendRedirect("/professor");break;
                            default:
                                response.sendRedirect("/login");break;
                        }
                    })
        )
        .logout(config -> config.logoutSuccessUrl("/"))
        .build();
    }

     @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}

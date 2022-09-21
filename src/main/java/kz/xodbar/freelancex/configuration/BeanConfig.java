package kz.xodbar.freelancex.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder defaultPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return ((request, response, authException) -> response.sendRedirect("https://www.google.com/"));
    }

    // TODO SET FRONT-END'S ENDPOINT LOGIN FOR EXPIRED TOKEN
}

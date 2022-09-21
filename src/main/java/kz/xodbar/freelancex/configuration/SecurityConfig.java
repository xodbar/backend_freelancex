package kz.xodbar.freelancex.configuration;

import kz.xodbar.freelancex.jwt.JwtConfigurer;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.jwt.JwtUserDetailsService;
import kz.xodbar.freelancex.util.CustomLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder defaultPasswordEncoder;
    private final JwtUserDetailsService jwtUserDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtConfigurer jwtConfigurer;

    private final AuthenticationEntryPoint unauthorizedEntryPoint;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable();
        http.csrf().disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(
                        "/api",
                        "/api/home",
                        "/api/about",
                        "/api/support",
                        "/api/health"
                ).permitAll();

        http.authorizeRequests().antMatchers(
                "/swagger-ui/",
                "/swagger-ui/springfox.css",
                "/swagger-ui/swagger-ui.css",
                "/swagger-ui/springfox.js",
                "/swagger-ui/swagger-ui-bundle.js",
                "/swagger-ui/swagger-ui-standalone-preset.js"
        ).hasRole("ADMIN");

        http.authorizeRequests()
                .antMatchers(
                        "/api/login",
                        "/api/login/auth",
                        "/api/register"
                ).permitAll();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/orders*")
                .permitAll();

        http.authorizeRequests()
                .antMatchers(
                        "/api/profile",
                        "/api/orders/mine"
                ).hasRole("USER");

        http.authorizeRequests()
                .antMatchers("/moderate*")
                .hasRole("MODERATOR");

        http.authorizeRequests()
                .antMatchers("/admin*")
                .hasRole("ADMIN");

//        http.exceptionHandling()
//                .authenticationEntryPoint(unauthorizedEntryPoint);

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .clearAuthentication(true);


        http.apply(jwtConfigurer);

//        http.authorizeRequests()
//                .antMatchers("/css/**", "/js/**")
//                .permitAll();
//
//        http.cors().and()
//                .csrf().disable().
//                authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/register/**")
//                .permitAll();
//
//        http.formLogin()
//                .loginProcessingUrl("/auth")
//                .defaultSuccessUrl("/")
//                .failureUrl("/login?error")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginPage("/login").permitAll();
//
//        http.logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?afterLogout");
    }
}

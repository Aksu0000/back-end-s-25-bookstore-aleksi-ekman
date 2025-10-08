package bes25.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService; // type of attribute -> interface

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/home", "/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/deleteBook/**").hasAuthority("ADMIN") // MVC DELETE
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasAuthority("ADMIN") // REST DELETE
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")                                        // oma login-sivu
                        .defaultSuccessUrl("/home", true)                       // loginin jälkeen booklist
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/home")
                        .invalidateHttpSession(true)
                        .permitAll()
                )

                // jos ei autentikoitu, ohjaa home-sivulle loginin sijaan
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/home");
                        })
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/h2-console/**")) // H2 ei tue CSRF:ää
                .headers(headers -> headers.frameOptions(frame -> frame.disable())); // H2 käyttää iframea

        return http.build();
    }
}

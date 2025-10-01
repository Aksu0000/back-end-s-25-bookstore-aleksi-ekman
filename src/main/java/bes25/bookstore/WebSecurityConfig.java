package bes25.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .requestMatchers("/api/**").permitAll()
                        //.requestMatchers("/booklist/**").authenticated()           
                        .requestMatchers("/deleteBook/**").hasAuthority("ADMIN")    // Delete vain ADMINille
                        .anyRequest().authenticated()                               // kaikki muut vaatii loginin
                )
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
                );

        return http.build();
    }
}

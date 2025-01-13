package produit.demo.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin1234"))
                        .roles("ADMIN")
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user123"))
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/products/list").hasAnyRole("USER", "ADMIN")  // Autoriser USER et ADMIN
                                .requestMatchers("/products/**").hasRole("ADMIN")  // ADMIN uniquement pour CRUD
                                .anyRequest().authenticated()  // Authentification pour le reste
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .successHandler(customSuccessHandler()) // Handler personnalisé
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // L'URL pour déclencher le logout (par défaut)
                                .logoutSuccessUrl("/login?logout") // Redirige après la déconnexion
                                .invalidateHttpSession(true) // Invalider la session HTTP
                                .deleteCookies("JSESSIONID") // Supprimer le cookie de session
                                .permitAll() // Permettre à tous d'accéder au logout
                )
                .csrf(csrf -> csrf.disable()); // Optionnel : désactive CSRF si non utilisé

        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {

                String redirectURL = "/products/list";  // Page des produits pour les utilisateurs

                // Si l'utilisateur est un ADMIN, rediriger vers /products
                if (authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                    redirectURL = "/products";  // Page des produits pour l'ADMIN
                }

                // Effectuer la redirection
                response.sendRedirect(redirectURL);
            }
        };

    }}








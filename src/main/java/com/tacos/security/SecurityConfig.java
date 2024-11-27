package com.tacos.security;

import com.tacos.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.tacos.data.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();

  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepo) {
    return username -> {
      User user = userRepo.findByUsername(username);
      if (user != null)
        return user;
      throw new UsernameNotFoundException("User ‘" + username + "’ not found");
    };
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/design", "/orders")
            .access(new WebExpressionAuthorizationManager("hasRole('USER')"))
            .requestMatchers("/", "/**")
            .access(new WebExpressionAuthorizationManager("permitAll()"))
            .requestMatchers(HttpMethod.POST, "/api/ingredients")
            .hasAuthority("SCOPE_writeIngredients")
            .requestMatchers(HttpMethod.DELETE, "/api/ingredients")
            .hasAuthority("SCOPE_deleteIngredients"))
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .formLogin(form -> form
            .loginPage("/login"))
        // .oauth2Login(auth -> auth
        // .loginPage("/login"))
        .logout(log -> log
            .logoutSuccessUrl("/"))
        .build();
  }

  /*
   * @Override
   * protected void configure(HttpSecurity http) throws Exception {
   * http
   * .authorizeHttpRequests(auth -> auth
   * .requestMatchers(HttpMethod.POST, "/api/ingredients")
   * .hasAuthority("SCOPE_writeIngredients")
   * .requestMatchers(HttpMethod.DELETE, "/api//ingredients")
   * .hasAuthority("SCOPE_deleteIngredients"))
   * .oauth2ResourceServer(oauth2 -> oauth2.jwt());
   * 
   * http.build();
   * }
   */
}

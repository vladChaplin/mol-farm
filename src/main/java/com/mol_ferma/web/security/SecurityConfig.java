package com.mol_ferma.web.security;

import com.mol_ferma.web.enums.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.mol_ferma.web.config.EncryptionConfig.passwordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        TODO: Настроить csrf так как есть post методы на сайты, защита от подделки межсайтового запроса
        http.csrf(csrf -> csrf.configure(http))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/",
                                "/login",
                                "/forgotPassword",
                                "/changePassword",
                                "/changePasswordError",
                                "/activate",
                                "/register",
                                "/register/save",
                                "/posts",
                                "/posts/{postId}",
                                "/assets/**",
                                "/favicon.ico")
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/regions/**"),
                                new AntPathRequestMatcher("/regions")).hasRole(RoleName.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/posts")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/posts", true);
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}

package in.abhi8290.helloworld.core.config;

import in.abhi8290.helloworld.auth.CustomOAuth2UserService;
import in.abhi8290.helloworld.auth.model.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import in.abhi8290.helloworld.shared.JwtFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;



    @Autowired
    private JwtFilter jwtFilter;

    // TO Do -> FIX THIS !!
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/**",
                                "/index",
                                "/index.html",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/favicon.ico"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/github")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/auth/oauth2/success", true)
                )
                .with(new OAuth2LoginConfigurer<>(), oauth2 -> oauth2
                        // .loginPage("/oauth2/authorization/google")
//                        .defaultSuccessUrl("/auth/oauth2/success", true)
                                .successHandler(customAuthenticationSuccessHandler)

//                        .defaultSuccessUrl("/result.html", true)
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                )            
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // ⬅️ Add JWT before auth


        return http.build();
    }
}
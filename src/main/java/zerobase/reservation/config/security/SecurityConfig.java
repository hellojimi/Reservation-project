package zerobase.reservation.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) //PreAuthorize 사용 여부
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) ->
                        csrf.disable()
                )
                .authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers(
                                        "/account/customer-join",
                                        "/account/manager-join",
                                        "/account/email-auth").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login
                                .loginPage("/account/login")        // 커스텀 로그인 페이지 url
                                .loginProcessingUrl("/loginProc")   // 로그인 처리
                                .defaultSuccessUrl("/account/dashboard", true)
                                .permitAll())
                .logout(Customizer.withDefaults())
        ;

        return http.build();
    }
}

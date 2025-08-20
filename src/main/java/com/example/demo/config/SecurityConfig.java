package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // 用 lambda 寫法啟用 CORS
                .csrf(csrf -> csrf.disable()) // 關閉 CSRF（開發環境才建議）
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // 登入及初始功能
                        .requestMatchers("/customer/**").permitAll() // 客戶可用
                        .requestMatchers("/employee/**").permitAll() // 員工可用
                        .requestMatchers("/common/**").permitAll() // 共用路徑
                        .anyRequest().permitAll() // 其他全部放行
                );
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/login").permitAll()
//                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
//                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")
//                        .requestMatchers("/common/**").hasAnyRole("CUSTOMER", "EMPLOYEE")
//                        .anyRequest().authenticated()
//                )

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

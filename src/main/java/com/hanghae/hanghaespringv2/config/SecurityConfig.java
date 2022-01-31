package com.hanghae.hanghaespringv2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // TODO 테스트 끝나면 적용해봐야겠다.
        http.authorizeRequests()
                .antMatchers("/", "/auth/**", "/js/**", "/posts/**")
                .permitAll()
                .anyRequest()
                .authenticated()

                .and()

                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/")

                .and()

                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login")
                .invalidateHttpSession(true);
    }
}

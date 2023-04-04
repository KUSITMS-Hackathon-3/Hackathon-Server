package com.example.hackathon.global.config.security;

import com.example.hackathon.global.config.security.jwt.JwtFilter;
import com.example.hackathon.global.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/image/**").permitAll()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/user/login").permitAll()
                .anyRequest().authenticated()
                .and()
                /**
                 * 우리는 JWT를 사용하는데 Session이 계속 켜져있으면
                 * 토큰을 안보내도 Sesssion때문에 자동으로 인증 처리가 되어버려
                 * 그래서 껐어
                 * 이제 토큰이 헤더로 안넘어오면 아무것도 안될거야
                 */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //jwt 사용을 위해 Sesssion 사용 해제
                .and()
                .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resource/**", "/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addExposedHeader("Authorization");
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

package com.sparta.miniproject_team4.security;

import com.sparta.miniproject_team4.filter.RestLoginFailureHandler;
import com.sparta.miniproject_team4.filter.RestLoginSuccessHandler;
import com.sparta.miniproject_team4.filter.RestLogoutSuccessHandler;
import com.sparta.miniproject_team4.filter.RestUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private RestLogoutSuccessHandler restLogoutSuccessHandler;

    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/h2-console/**").disable();
        http
                .httpBasic().disable()
                .cors()
                .configurationSource(corsConfigurationSource());

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/**").permitAll()
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                .antMatchers("/api/**").permitAll()
                // 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/users/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                // 그 외 어떤 요청이든 '인증'
//                .anyRequest().authenticated();
                .anyRequest().permitAll();
        // [로그인 기능]
        http.formLogin().disable();
        http.addFilterAt(getAuthenticationFilter(), RestUsernamePasswordAuthenticationFilter.class);

        // [로그아웃 기능]
        http.logout()
                // 로그아웃 요청 처리 URL
                .logoutUrl("/api/users/logout")
                .logoutSuccessHandler(restLogoutSuccessHandler)
                .permitAll();
        http.exceptionHandling();
    }

    protected RestUsernamePasswordAuthenticationFilter getAuthenticationFilter() {
        RestUsernamePasswordAuthenticationFilter authFilter = new RestUsernamePasswordAuthenticationFilter();
        try {
            authFilter.setFilterProcessesUrl("/api/users/login"); // 로그인에 대한 POST 요청을 받을 url을 정의합니다. 해당 코드가 없으면 정상적으로 작동하지 않습니다.
            authFilter.setUsernameParameter("email");
            authFilter.setPasswordParameter("password");
            authFilter.setAuthenticationManager(this.authenticationManagerBean());
            authFilter.setAuthenticationSuccessHandler(successHandler());
            authFilter.setAuthenticationFailureHandler(failureHandler());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return authFilter;
    }
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:3000"); // local 테스트 시
        configuration.addAllowedOrigin("http://miniproject-mydiary.s3-website.ap-northeast-2.amazonaws.com/"); // local 테스트 시
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");
//        configuration.addAllowedOriginPattern("http://localhost:3000"); // 배포 전 모두 허용
        configuration.addAllowedOriginPattern("http://miniproject-mydiary.s3-website.ap-northeast-2.amazonaws.com/"); // 배포 전 모두 허용
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new RestLoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return new RestLoginFailureHandler();
    }
}
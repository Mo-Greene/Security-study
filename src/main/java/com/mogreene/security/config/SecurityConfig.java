package com.mogreene.security.config;

import com.mogreene.security.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  //security 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //@Secured 어노테이션 활성화, @PreAuthorize 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    //해당 메서드의 리턴되는 오브젝트를 ioc로 등록하여 어디서든 사용가능
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()

                .and()
                    .formLogin().loginPage("/loginForm")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/");

        return http.build();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests()
//                //user 로 들어오면 인가
//                .antMatchers("/user/**").authenticated()
//                //manager 로 들어오면 admin or manager
//                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
//                //admin 으로 들어오면 admin
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .anyRequest().permitAll()
//
//                .and()
//                //login security
//                //login 페이지로 접속
//                .formLogin().loginPage("/loginForm")
//                .loginProcessingUrl("/login")  // /login 주소가 호출이 되면 시큐리티가 대신 로그인을 진행해줌
//                //로그인 전 url이 있다면 그 url로 감
//                .defaultSuccessUrl("/")
//
//                .and()
//                .oauth2Login()
//                .loginPage("/loginForm")
//                //구글로그인 후처리
//                // 1.코드받기(인증) 2.엑세스토큰(권한) 3.사용자프로필 정보 가져옴
//                // 4-1.그 정보를 토대로 회원가입 자동 진행
//                // 4-2.추가적인 회원가입 절차(필요할 경우)
//                // 구글로그인 Tip.엑세스토큰 + 사용자프로필정보 받아옴
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService)
//        ;
//    }
}

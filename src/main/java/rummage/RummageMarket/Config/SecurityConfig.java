package rummage.RummageMarket.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Config.Auth.PrincipalDetailsService;
import rummage.RummageMarket.Config.Oauth.OAuth2DetailsService;

// 시큐리티 설정
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다는 뜻
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public final OAuth2DetailsService oAuth2DetailsService;

    private final AuthenticationFailureHandler customAuthFailureHandler;

    private final PrincipalDetailsService principalDetailService;

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.rememberMe() // 로그인 유지
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(86400 * 30)
                .alwaysRemember(false)
                .userDetailsService(principalDetailService);
        http.authorizeRequests() // 사용자 권한에 따른 URI 접근 제어
                .antMatchers("/", "/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin() // 로그인
                .loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signin")
                .defaultSuccessUrl("/")
                .failureHandler(customAuthFailureHandler)
                .and()
                .oauth2Login() // 소셜로그인
                .userInfoEndpoint()
                .userService(oAuth2DetailsService);
    }
}

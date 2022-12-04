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

@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다는 뜻
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public final OAuth2DetailsService oAuth2DetailsService;

    private final AuthenticationFailureHandler customAuthFailureHandler;

    private final PrincipalDetailsService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.rememberMe()
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(86400 * 30)
                .alwaysRemember(false)
                .userDetailsService(principalDetailService);
        http.authorizeRequests()
                .antMatchers("/", "/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signin") // 로그인 요청인지 아닌지 판단 -> 로그인 요청이면 UserDetailsService가 낚아챔.
                .defaultSuccessUrl("/")
                .failureHandler(customAuthFailureHandler)
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(oAuth2DetailsService);
    }
}

package rummage.RummageMarket.Config.Auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
import rummage.RummageMarket.Domain.User.User;


// 세션
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private static final long serialVersionUID = 1L;

    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
    }

    // 권한 목록 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() -> {
            return user.getRole();
        });
        return collector;
    }

    // 패스워드 리턴
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 유저네임 리턴
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정만료여부 리턴 - true를 리턴하면 만료되지않음
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정잠김여부 리턴 - true를 리턴하면 계정이 잠겨있지않음
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정의 패스워드 만료여부 리턴 - true를 리턴하면 패스워드가 만료되지않음
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용가능 여부 리턴 - true를 리턴하면 사용가능한 계정임.
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 소셜로그인시 유저 정보 리턴
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 소셜로그인시 유저 이름 리턴
    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

}

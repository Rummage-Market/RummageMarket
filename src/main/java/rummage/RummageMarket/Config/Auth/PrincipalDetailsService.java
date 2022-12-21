package rummage.RummageMarket.Config.Auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Domain.User.UserRepository;

// 로그인 진행
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;

    // username으로 확인, UserDetails타입을 세션 생성
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
        } else {
            return new PrincipalDetails(userEntity);
        }
    }
}
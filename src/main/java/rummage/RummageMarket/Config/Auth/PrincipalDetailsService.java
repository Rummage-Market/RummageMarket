package rummage.RummageMarket.Config.Auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Domain.User.UserRepository;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// password는 시큐리티가 비교
		// 리턴이 잘되면 자동으로 세션을 만듬 -> UserDetails로 리턴해야된다.
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			return null;
		} else {
			return new PrincipalDetails(userEntity);
		}
	}

}

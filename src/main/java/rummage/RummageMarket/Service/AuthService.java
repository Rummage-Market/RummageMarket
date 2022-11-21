package rummage.RummageMarket.Service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Domain.User.UserRepository;

@RequiredArgsConstructor
@Service
public class AuthService {
	
	private final UserRepository userRepository;

	public User signUp(User user) {
		User userEntity = userRepository.save(user);
		return userEntity;
	}
}

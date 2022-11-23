package rummage.RummageMarket.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Domain.User.UserRepository;
import rummage.RummageMarket.Handler.Ex.CustomException;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	public User userprofile(int id) {
		User userEntity = userRepository.findById(id).orElseThrow(()->{
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});
		return userEntity;
	}

}

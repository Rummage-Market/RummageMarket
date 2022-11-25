package rummage.RummageMarket.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rummage.RummageMarket.Domain.SubScribe.SubscribeRepository;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Domain.User.UserRepository;
import rummage.RummageMarket.Handler.Ex.CustomException;
import rummage.RummageMarket.Handler.Ex.CustomValidationApiException;
import rummage.RummageMarket.Web.Dto.User.UserProfileDto;
import rummage.RummageMarket.Web.Dto.User.UserUpdateDto;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SubscribeRepository subscribeRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional(readOnly = true)
	public UserProfileDto userprofile(int pageUserId, int principalId) {
	    UserProfileDto dto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId);
        dto.setImageCount(userEntity.getPosts().size());

        int subscribeState = subscribeRepository.subscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.subscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState == 1);
        dto.setSubscribeCount(subscribeCount);

        return dto;
    }
	
	@Transactional
	public User updateUser(int id, User user) {
	    User userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new CustomValidationApiException("찾을 수 없는 id입니다.");
        });
	    userEntity.setUsername(user.getUsername());
	    String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);
	    userEntity.setNickname(user.getNickname());
	    userEntity.setBio(user.getBio());
	    userEntity.setEmail(user.getEmail());
	    return userEntity;
	}
}

package rummage.RummageMarket.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import rummage.RummageMarket.Domain.Neighbor.NeighborRepository;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Domain.User.UserRepository;
import rummage.RummageMarket.Handler.Ex.CustomApiException;
import rummage.RummageMarket.Handler.Ex.CustomException;
import rummage.RummageMarket.Handler.Ex.CustomValidationApiException;
import rummage.RummageMarket.Web.Dto.User.UserProfileDto;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NeighborRepository neighborRepository;

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
        dto.setPostCount(userEntity.getPosts().size());

        int neighborState = neighborRepository.neighborState(principalId, pageUserId);
        int neighborCount = neighborRepository.neighborCount(pageUserId);

        dto.setNeighborCount(neighborCount);
        dto.setNeighborState(neighborState == 1);

        userEntity.getPosts().forEach((post) -> {
            post.setInterestCount(post.getInterest().size());
            post.setCommentCount(post.getComments().size());
        });

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

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public User profileImageUrlUpdate(int principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID(); // uuid
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename(); // 1.jpg
        System.out.println("이미지 파일이름 : " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신, I/O -> 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
            // throw -> return 으로 변경
            return new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImage(imageFileName);

        return userEntity;
    } // 더티체킹으로 업데이트 됨.

    @Transactional(readOnly = true)
    public boolean usernameCheck(String username) {
        return userRepository.existsByUsername(username);
    }
}

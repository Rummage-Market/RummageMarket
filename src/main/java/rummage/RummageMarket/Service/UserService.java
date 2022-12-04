package rummage.RummageMarket.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

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

    @Autowired
    AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

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

    @Transactional
    public User profileImageUrlUpdate(int principalId, MultipartFile profileImageFile, String userProfileUrl) {

        if (userProfileUrl != null) {
            String key = userProfileUrl.substring(54);
            amazonS3Client.deleteObject(bucket, key);
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(profileImageFile.getContentType());
        objectMetadata.setContentLength(profileImageFile.getSize());

        String originalFilename = profileImageFile.getOriginalFilename();// 김영광.jpg
        int index = originalFilename.lastIndexOf(".");// '.'이라는 문자가 발견되는 위치에 해당하는 index값(위치값) = 3
        String ext = originalFilename.substring(index + 1);// index + 1 = 4 -> jpg

        String storeFileName = UUID.randomUUID() + "." + ext;// uuid.jpg
        String key = "upload/" + storeFileName;

        try (InputStream inputStream = profileImageFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();

        User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
            // throw -> return 으로 변경
            return new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImage(storeFileUrl);

        return userEntity;
    }

    @Transactional(readOnly = true)
    public boolean usernameCheck(String username) {
        return userRepository.existsByUsername(username);
    }
}

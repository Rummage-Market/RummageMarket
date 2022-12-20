package rummage.RummageMarket.Web.Api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Service.NeighborService;
import rummage.RummageMarket.Service.UserService;
import rummage.RummageMarket.Web.Dto.CMRespDto;
import rummage.RummageMarket.Web.Dto.Neighbor.NeighborDto;
import rummage.RummageMarket.Web.Dto.User.UserUpdateDto;

@RestController
public class UserApiController {

    @Autowired
    NeighborService neighborService;

    @Autowired
    UserService userService;

    // 회원 프로필사진 변경
    @PutMapping("/api/user/{principalId}/profileImage")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String userProfileUrl = principalDetails.getUser().getProfileImage();
        User userEntity = userService.profileImageUrlUpdate(principalId, profileImageFile, userProfileUrl);
        principalDetails.setUser(userEntity); // 세션 변경
        return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진변경 성공", null), HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/neighbor")
    public ResponseEntity<?> neighborList(@PathVariable int pageUserId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<NeighborDto> neighborDto = neighborService.neighborList(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 불러오기 성공", neighborDto), HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id, @Valid UserUpdateDto userUpdateDto, BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        User userEntity = userService.updateUser(id, userUpdateDto.toEntity());
        principalDetails.setUser(userEntity);
        return new CMRespDto<>(1, "회원수정완료", userEntity);
    }
    
    // 회원 탈퇴
    @DeleteMapping("/api/user/{userId}")
    public ResponseEntity<?> delete(@PathVariable int userId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        userService.deleteUser(userId, principalDetails.getUser().getId());
        
        return new ResponseEntity<>(new CMRespDto<>(1, "회원탈퇴완료", null), HttpStatus.OK);
    }
}
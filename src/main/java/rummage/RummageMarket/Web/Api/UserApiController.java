package rummage.RummageMarket.Web.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Service.SubscribeService;
import rummage.RummageMarket.Web.Dto.CMRespDto;
import rummage.RummageMarket.Web.Dto.SubScribe.SubscribeDto;
import rummage.RummageMarket.Web.Dto.User.UserUpdateDto;

@RestController
public class UserApiController {
	
	@Autowired
	SubscribeService subscribeService;
	
	@GetMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserId,@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		List<SubscribeDto> subscribeDto = subscribeService.subScribeList(principalDetails.getUser().getId(),pageUserId);
		
		return new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보 리스트 불러오기 성공",subscribeDto),HttpStatus.OK);
	}
	
    @PutMapping("/api/user/{id}")
    public String update(UserUpdateDto userUpdateDto) {
        System.out.println(userUpdateDto);
        return "ok";
    }
}
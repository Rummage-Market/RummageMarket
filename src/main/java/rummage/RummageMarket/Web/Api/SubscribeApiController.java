package rummage.RummageMarket.Web.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Service.SubscribeService;
import rummage.RummageMarket.Web.Dto.CMRespDto;

@RestController
public class SubscribeApiController {
	
	@Autowired
	SubscribeService subScribeService; 
	
	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable int toUserId){
		subScribeService.subscribe(principalDetails.getUser().getId(),toUserId);
		return null;
	}
	
	@DeleteMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PathVariable int toUserId) {
		subScribeService.unsubscribe(principalDetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독취소하기 성공", null), HttpStatus.OK);
	}
}
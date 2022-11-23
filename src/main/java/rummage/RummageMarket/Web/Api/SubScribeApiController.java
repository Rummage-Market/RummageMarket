package rummage.RummageMarket.Web.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Service.SubScribeService;

@RestController
public class SubScribeApiController {
	
	@Autowired
	SubScribeService subScribeService; 
	
	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable int toUserId){
		subScribeService.subscribe(principalDetails.getUser().getId(),toUserId);
		return null;
	}
}

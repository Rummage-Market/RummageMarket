package rummage.RummageMarket.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Service.PostService;
import rummage.RummageMarket.Web.Dto.Post.PostUploadDto;

@RequiredArgsConstructor
@Controller
public class PostController {
	
	@Autowired
	PostService postService;
	
	@PostMapping("/post")
	public String imageUpload(PostUploadDto postUploadDto,@AuthenticationPrincipal PrincipalDetails principalDetails) {
				
		//서비스 호출
		postService.upload(postUploadDto, principalDetails);
		
		return "redirect:/user/"+principalDetails.getUser().getId();
	}
}

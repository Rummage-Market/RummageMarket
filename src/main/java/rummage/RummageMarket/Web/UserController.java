package rummage.RummageMarket.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Service.UserService;
import rummage.RummageMarket.Web.Dto.User.UserProfileDto;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    //프로필페이지 리턴함
    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, Model model,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserProfileDto dto = userService.userprofile(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("dto", dto);
        return "user/profile";
    }

    //회원수정페이지 리턴함
    @GetMapping("/user/{pageUserId}/update")
    public String update(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return "user/update";
    }
}
package rummage.RummageMarket.Web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Service.AuthService;
import rummage.RummageMarket.Service.UserService;
import rummage.RummageMarket.Web.Dto.CMRespDto;
import rummage.RummageMarket.Web.Dto.Auth.SignupDto;

@RequiredArgsConstructor
@Controller
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    // 로그인 페이지 리턴
    @GetMapping("/auth/signin")
    public String signinForm(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "auth/signin";
    }

    // 회원가입 페이지 리턴
    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }
    
    // 회원가입 Insert
    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {

        User user = signupDto.toEntity();
        authService.signUp(user);

        return "auth/signin";
    }

    //유저네임 중복확인 
    @PostMapping("/auth/usernameCheck")
    @ResponseBody
    public ResponseEntity<?> usernameCheck(@RequestParam("username") String username) {
        boolean chq = userService.usernameCheck(username);
        return new ResponseEntity<>(new CMRespDto<>(1, "유저네임 중복 확인 성공!", chq), HttpStatus.OK);
    }

}
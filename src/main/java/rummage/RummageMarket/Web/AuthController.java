package rummage.RummageMarket.Web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {

        User user = signupDto.toEntity();
        User userEntity = authService.signUp(user);

        return "auth/signin";
    }
    
    
    @PostMapping("/auth/usernameCheck")
    @ResponseBody
    public ResponseEntity<?> usernameCheck(@RequestParam("username") String username) {
        boolean chq = userService.usernameCheck(username);
        return new ResponseEntity<>(new CMRespDto<>(1, "유저네임 중복 확인 성공!", chq), HttpStatus.OK);
    }
}
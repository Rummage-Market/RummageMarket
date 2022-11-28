package rummage.RummageMarket.Web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Service.AuthService;
import rummage.RummageMarket.Web.Dto.Auth.SignupDto;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

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
}
package rummage.RummageMarket.Web;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Handler.Ex.CustomValidationException;
import rummage.RummageMarket.Service.AuthService;
import rummage.RummageMarket.Web.Dto.Auth.SignupDto;

@RequiredArgsConstructor
@Controller
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	private final AuthService authService;
	
	@GetMapping("/")
	public @ResponseBody String main() {
		return "로그인 잘되었는지 테스트!";
	}

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
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("=================================");
				System.out.println(error.getDefaultMessage());
				System.out.println("=================================");
			}
			throw new CustomValidationException("유효성검사 실패!", errorMap);
		} else {
			log.info(signupDto.toString());
			User user = signupDto.toEntity();
			log.info(user.toString());
			User userEntity = authService.signUp(user);
			log.info(userEntity.toString());
			return "auth/signin";
		}
		
		
	}
}

package rummage.RummageMarket.Web;

import org.springframework.stereotype.Controller;

@Controller
public class AuthController {

	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
}

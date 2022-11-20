package rummage.RummageMarket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewControllerTest {

	@GetMapping("/auth/signup")
	public String signupPage() {
		return "auth/signup";
	}
	
	@GetMapping("/auth/signin")
	public String signinPage() {
		return "auth/signin";
	}
	
}

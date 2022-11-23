package rummage.RummageMarket.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id, Model model) {
		User userEntity = userService.userprofile(id);
		model.addAttribute("user",userEntity);
		return "user/profile";
	}
  
  @GetMapping("/user/profile")
	public String profile() {
		return "user/profile";
	}
 
	@GetMapping("/user/update")
	public String update() {
		return "user/update";
	}
}


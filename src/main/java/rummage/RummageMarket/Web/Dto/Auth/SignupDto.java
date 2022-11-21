package rummage.RummageMarket.Web.Dto.Auth;

import lombok.Data;

@Data
public class SignupDto {
	private String username;
	private String password;
	private String nickname;
}

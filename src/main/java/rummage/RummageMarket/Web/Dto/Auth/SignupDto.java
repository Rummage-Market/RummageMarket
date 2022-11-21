package rummage.RummageMarket.Web.Dto.Auth;

import lombok.Data;
import rummage.RummageMarket.Domain.User.User;

@Data
public class SignupDto {
	private String username;
	private String password;
	private String nickname;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.nickname(nickname)
				.build();
	}
}

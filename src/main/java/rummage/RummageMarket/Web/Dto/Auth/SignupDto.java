package rummage.RummageMarket.Web.Dto.Auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import rummage.RummageMarket.Domain.User.User;

@Data
public class SignupDto {
	
	@Size(min = 2, max = 30)
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String nickname;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.nickname(nickname)
				.build();
	}
}

package rummage.RummageMarket.Web.Dto.Auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import rummage.RummageMarket.Domain.User.User;

@Data
public class SignupDto {
    
	@Size(min = 2, max = 30, message = "유저네임은 2자 이상 30자 이하로 입력해주세요.")
	@NotBlank(message = "유저네임을 입력해주세요.")
	private String username;
	@NotBlank(message = "패스워드를 입력해주세요.")
	private String password;
	@NotBlank(message = "닉네임을 입력해주세요.")
	private String nickname;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.nickname(nickname)
				.build();
	}
}
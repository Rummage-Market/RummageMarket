package rummage.RummageMarket.Web.Dto.User;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import rummage.RummageMarket.Domain.User.User;

@Data
public class UserUpdateDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    // 자기소개
    private String bio;

    // 실질적으로 사용되는 변수는 아니지만, sso 로그인시 원활하게 조작하기 위한 변수
    private String email;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .bio(bio)
                .email(email)
                .build();
    }
}

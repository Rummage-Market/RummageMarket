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
    private String bio;
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

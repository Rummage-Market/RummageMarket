package rummage.RummageMarket.Web.Dto.User;

import lombok.Data;
import rummage.RummageMarket.Domain.User.User;

@Data
public class UserUpdateDto {
    private String username;
    private String password;
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

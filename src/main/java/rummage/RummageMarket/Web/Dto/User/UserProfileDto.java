package rummage.RummageMarket.Web.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rummage.RummageMarket.Domain.User.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserProfileDto {
    private User user;
    
    // 프로필 페이지의 user 와 로그인된 user가 같은지 상태값을 전달하는 변수
    private boolean pageOwnerState;
    
    // 게시글의 갯수를 담아낸 변수
    private int postCount;

    // 프로필 페이지의 user 를 로그인된 user가 이웃을 맺었는지 상태값을 전달하는 변수
    private boolean neighborState;
    
    private int neighborCount;
}
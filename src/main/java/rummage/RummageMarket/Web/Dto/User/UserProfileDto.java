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
    private boolean pageOwnerState;
    private int postCount;
    private boolean neighborState;
    private int neighborCount;
}
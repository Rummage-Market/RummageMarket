package rummage.RummageMarket.Web.Dto.Neighbor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NeighborDto {
	private Integer userId;
	private String nickname;
	private String profileImageUrl;
	private Integer neighborState; 
	private Integer equalUserState;
}

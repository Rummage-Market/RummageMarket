package rummage.RummageMarket.Web.Dto.SubScribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	private Integer userId;
	private String Username;
	private String profileImageUrl;
	private Integer subscribeState; 
	private Integer equalUserState;
}

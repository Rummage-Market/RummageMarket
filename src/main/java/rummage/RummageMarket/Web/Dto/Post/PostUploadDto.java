package rummage.RummageMarket.Web.Dto.Post;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import rummage.RummageMarket.Domain.Post.Post;
import rummage.RummageMarket.Domain.User.User;

@Data
public class PostUploadDto {
	private MultipartFile file;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String content;
	
    // '서울특별시 강남구' 중 '서울특별시'를 뜻한다.
	@NotBlank
	private String address1;
	
    // '서울특별시 강남구' 중 '강남구'를 뜻한다.
	@NotBlank
	private String address2; 
	
	private String item; 
	private int price; 
	
	
	public Post toEntity(String postImageUrl, User user) {
		return Post.builder()
				.title(title)
				.content(content)
				.address1(address1)
				.address2(address2)
				.item(item)
				.price(price)
				.imageUrl(postImageUrl)
				.user(user)
				.build();
	}
}
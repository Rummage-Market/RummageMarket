package rummage.RummageMarket.Web.Dto.Post;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import rummage.RummageMarket.Domain.Post.Post;
import rummage.RummageMarket.Domain.User.User;

@Data
public class PostUpdateDto {
    
    private MultipartFile file;   
    @NotBlank
    private String title;   
    @NotBlank
    private String content;    
    @NotBlank
    private String address1;
    @NotBlank
    private String address2; 
    private String item; 
    private int price; 
}

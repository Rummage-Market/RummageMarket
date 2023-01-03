package rummage.RummageMarket.Web.Dto.Post;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostUpdateDto {

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

    // 판매 할 상품명 
    private String item;
    private int price;
}

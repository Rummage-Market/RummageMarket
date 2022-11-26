package rummage.RummageMarket.Web.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Domain.Post.Post;
import rummage.RummageMarket.Service.PostService;
import rummage.RummageMarket.Web.Dto.CMRespDto;

@RestController
@RequiredArgsConstructor
public class PostApiController {
    
    @Autowired
    PostService postService;
    
    //필터링 없이 제일 최신순의 게시글
    @GetMapping("/api/post")
    public ResponseEntity<?> imageStory() {
        List<Post> posts= postService.postList();
        return new ResponseEntity<>(new CMRespDto<>(1, "이미지스토리 불러오기 성공", posts), HttpStatus.OK);
    }

}

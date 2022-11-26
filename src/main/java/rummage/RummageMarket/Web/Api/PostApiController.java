package rummage.RummageMarket.Web.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> postStory(@PageableDefault(size = 3) Pageable pageable) {
        Page<Post> posts= postService.postList(pageable);
        return new ResponseEntity<>(new CMRespDto<>(1, "게시글 불러오기 성공", posts), HttpStatus.OK);
    }
}
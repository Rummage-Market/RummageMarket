package rummage.RummageMarket.Web.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Domain.Post.Post;
import rummage.RummageMarket.Service.InterestService;
import rummage.RummageMarket.Service.PostService;
import rummage.RummageMarket.Web.Dto.CMRespDto;

@RestController
public class PostApiController {
    
    @Autowired
    PostService postService;
    
    @Autowired
    InterestService interestService;
    
    //필터링 없이 제일 최신순의 게시글
    @GetMapping("/api/post")
    public ResponseEntity<?> postStory(@PageableDefault(size = 3) Pageable pageable, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Page<Post> posts= postService.postList(pageable, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "게시글 불러오기 성공", posts), HttpStatus.OK);
    }
    
    @PostMapping("/api/post/{postId}/interest")
    public ResponseEntity<?> interest(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int postId) {
        interestService.haveInterest(postId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "interest 성공", null), HttpStatus.OK);
    }
    
    @DeleteMapping("/api/post/{postId}/interest")
    public ResponseEntity<?> disinterest(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int postId) {
        interestService.haveNoInterest(postId,  principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "disinterest 성공", null), HttpStatus.OK);
    }
}
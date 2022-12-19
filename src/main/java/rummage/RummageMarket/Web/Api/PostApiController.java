package rummage.RummageMarket.Web.Api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Domain.Post.Post;
import rummage.RummageMarket.Handler.Ex.CustomValidationException;
import rummage.RummageMarket.Service.InterestService;
import rummage.RummageMarket.Service.PostService;
import rummage.RummageMarket.Web.Dto.CMRespDto;
import rummage.RummageMarket.Web.Dto.Post.PostUploadDto;

@RestController
public class PostApiController {

    @Autowired
    PostService postService;

    @Autowired
    InterestService interestService;
    
    @PutMapping("/api/post/{postid}")
    public CMRespDto<?> postUpdate(@PathVariable int postid,@RequestPart MultipartFile file,@Valid PostUploadDto postUploadDto, BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Post post = postService.update(postid,file,postUploadDto, principalDetails);
        return new CMRespDto<>(1, "게시글수정 성공", post);
    }

    // 게시글 삭제
    @DeleteMapping("api/post/{postId}")
    public ResponseEntity<?> postDelete(@PathVariable int postId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        postService.delete(postId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "게시글삭제 성공", null), HttpStatus.OK);
    }
    
    // 필터링 없이 제일 최신순의 게시글
    @GetMapping("/api/post")
    public ResponseEntity<?> postStory(@PageableDefault(size = 3) Pageable pageable,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Page<Post> posts = postService.postList(pageable, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "게시글 불러오기 성공", posts), HttpStatus.OK);
    }

    // 게시글 자세히 보기
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<?> postStory(@PathVariable int postId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Post post = postService.detailpost(principalDetails.getUser().getId(), postId);
        return new ResponseEntity<>(new CMRespDto<>(1, "게시글 불러오기 성공", post), HttpStatus.OK);
    }

    @PostMapping("/api/post/{postId}/interest")
    public ResponseEntity<?> interest(@AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable int postId) {
        interestService.haveInterest(postId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "interest 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/post/{postId}/interest")
    public ResponseEntity<?> disinterest(@AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable int postId) {
        interestService.haveNoInterest(postId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "disinterest 성공", null), HttpStatus.OK);
    }

    @GetMapping("/api/post/search")
    public ResponseEntity<?> searchPost(
            @PageableDefault(size = 100) Pageable pageable,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            String address1,
            String address2,
            String item) {
        Page<Post> searchedposts = postService.searchPostList(pageable, principalDetails.getUser().getId(), address1,
                address2, item);
        return new ResponseEntity<>(new CMRespDto<>(1, "검색된 게시글 불러오기 성공", searchedposts), HttpStatus.OK);
    }
}
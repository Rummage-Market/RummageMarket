package rummage.RummageMarket.Web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Domain.Post.Post;
import rummage.RummageMarket.Handler.Ex.CustomValidationException;
import rummage.RummageMarket.Service.PostService;
import rummage.RummageMarket.Web.Dto.Post.PostUploadDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    @Autowired
    PostService postService;

    // 메인 페이지 + 인기게시글을 리턴함
    @GetMapping({ "/", "post/main" })
    public String mainPage(Model model) {
        List<Post> posts = postService.popularPost();
        model.addAttribute("posts", posts);
        return "post/main";
    }

    // 전체게시글 페이지 리턴함
    @GetMapping("post/story")
    public String story() {
        return "post/story";
    }

    // 게시글 검색 페이지 리턴함
    @GetMapping("post/search")
    public String search() {
        return "post/search";
    }

    // 한개의 게시글 페이지 리턴함
    @GetMapping("/post/{postId}")
    public String detailstory(@PathVariable int postId, Model model,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Post post = postService.detailpost(principalDetails.getUser().getId(), postId);
        model.addAttribute("post", post);
        return "post/detailstory";
    }
    
    // 게시글 작성 페이지 리턴함
    @GetMapping("/post/upload")
    public String upload() {
        return "post/upload";
    }

    // 게시글 작성하고 Model에 담아 redirect 리턴함
    @PostMapping("/post")
    public String postUpload(@RequestPart MultipartFile file, @Valid PostUploadDto postUploadDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (postUploadDto.getFile().isEmpty()) {
            throw new CustomValidationException("이미지는 반드시 첨부해주세요.", null);
        }

        postService.upload(file, postUploadDto, principalDetails);
        return "redirect:/user/" + principalDetails.getUser().getId();
    }

    // 게시글 수정 페이지 리턴함
    @GetMapping("/post/{postid}/update")
    public String postmodify(@PathVariable int postid, Model model) {
        Post post = postService.findByPostId(postid);
        model.addAttribute("post", post);
        return "post/postmodify";
    }
}
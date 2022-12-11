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

    @GetMapping({ "/", "post/main" })
    public String mainPage(Model model) {
        List<Post> posts = postService.popularPost();
        model.addAttribute("posts", posts);
        return "post/main";
    }

    @GetMapping("post/story")
    public String story() {
        return "post/story";
    }
    
    @GetMapping("post/search")
    public String search() {
        return "post/search";
    }
    
    @GetMapping("/post/{postId}")
    public String detailstory(@PathVariable int postId, Model model,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Post post = postService.detailpost(principalDetails.getUser().getId(), postId);

        model.addAttribute("post", post);
        return "post/detailstory";
    }

    @PostMapping("/post")
    public String postUpload(@RequestPart MultipartFile file,@Valid PostUploadDto postUploadDto, BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (postUploadDto.getFile().isEmpty()) {
            throw new CustomValidationException("이미지는 반드시 첨부해주세요.", null);
        }

        postService.upload(file,postUploadDto, principalDetails);
        return "redirect:/user/" + principalDetails.getUser().getId();
    }

    @GetMapping("/post/upload")
    public String upload() {
        return "post/upload";
    }
}
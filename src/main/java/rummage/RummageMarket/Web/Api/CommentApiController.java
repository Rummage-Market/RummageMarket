package rummage.RummageMarket.Web.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Config.Auth.PrincipalDetails;
import rummage.RummageMarket.Domain.Comment.Comment;
import rummage.RummageMarket.Service.CommentService;
import rummage.RummageMarket.Web.Dto.CMRespDto;
import rummage.RummageMarket.Web.Dto.Comment.CommentDto;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    
    @Autowired
    CommentService commentService; 
    
    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@RequestBody CommentDto commentDto,@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("======controller 에서 CommentDto");
        System.out.println(commentDto);
        
        Comment comment = commentService.commentSave(commentDto.getContent(),commentDto.getPostId(),principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글쓰기 성공",comment), HttpStatus.CREATED);
    }
    
    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id){
        return null;
    }

}

package rummage.RummageMarket.Web.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Service.CommentService;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    
    @Autowired
    CommentService commentService; 
    
    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(){
        return null;
    }
    
    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id){
        return null;
    }

}

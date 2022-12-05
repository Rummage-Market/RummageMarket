package rummage.RummageMarket.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.Comment.Comment;
import rummage.RummageMarket.Domain.Comment.CommentRepository;
import rummage.RummageMarket.Domain.Post.Post;
import rummage.RummageMarket.Domain.User.User;
import rummage.RummageMarket.Domain.User.UserRepository;
import rummage.RummageMarket.Handler.Ex.CustomApiException;

@RequiredArgsConstructor
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Comment commentSave(String content, int postId, int UserId) {

        Post post = new Post();
        post.setId(postId);

        User userEntity = userRepository.findById(UserId).orElseThrow(() -> {
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(userEntity);

        return commentRepository.save(comment);
    }

    public void commentDelete(int id) {
        commentRepository.deleteById(id);
    }
}

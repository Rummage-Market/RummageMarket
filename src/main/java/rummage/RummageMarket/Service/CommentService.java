package rummage.RummageMarket.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.Comment.Comment;
import rummage.RummageMarket.Domain.Comment.CommentRepository;

@RequiredArgsConstructor
@Service
public class CommentService {
    
    @Autowired
    CommentRepository commentRepository;
    
    @Transactional
    public Comment 댓글쓰기() {
        return null;
    }
    
    @Transactional
    public Comment 댓글삭제() {
        return null;
    }

}

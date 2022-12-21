package rummage.RummageMarket.Domain.Post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM post ORDER BY id DESC", nativeQuery = true)
    Page<Post> findPostList(Pageable pageable);

    @Query(value = "SELECT p.* FROM post p INNER JOIN (SELECT post_id, COUNT(post_id) likeCount FROM interest GROUP BY post_id) c on p.id = c.post_id ORDER BY likeCount DESC LIMIT 6;", nativeQuery = true)
    List<Post> findPopularPostList();
    
    @Query(value = "SELECT * FROM post WHERE address1 LIKE %:address1% AND address2 LIKE %:address2% AND item LIKE %:item% ORDER BY id DESC", nativeQuery = true)
    Page<Post> searchPostList(Pageable pageable, String address1, String address2, String item);
    
}

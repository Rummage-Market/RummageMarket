package rummage.RummageMarket.Domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer>{
    
    @Query(value = "SELECT * FROM post ORDER BY id DESC", nativeQuery = true)
    Page<Post> findPostList(Pageable pageable);
    
    @Query(value = "SELECT * FROM post WHERE address1 LIKE ':address1' AND address2 LIKE ':address2' AND item LIKE '%:item%' ORDER BY id DESC", nativeQuery = true)
    Page<Post> searchPostList(Pageable pageable, String address1, String address2, String item);
}

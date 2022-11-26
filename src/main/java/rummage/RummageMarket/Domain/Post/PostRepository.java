package rummage.RummageMarket.Domain.Post;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer>{
    
    @Query(value = "SELECT * FROM post ORDER BY id DESC;", nativeQuery = true)
    List<Post> findAllIdDesc(Pageable pageable);
}

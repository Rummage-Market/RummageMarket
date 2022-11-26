package rummage.RummageMarket.Domain.Interest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InterestRepostiory extends JpaRepository<Interest, Integer>{
    
    @Modifying
    @Query(value = "INSERT INTO likes(postId, userId) VALUES(:postId, :principalId)", nativeQuery = true)
    int mInterest(int imageId, int principalId);
    
    @Modifying
    @Query(value = "DELETE FROM likes WHERE postId = :postId AND userId = :principalId", nativeQuery = true)
    int mDisinterest(int imageId, int principalId);
}

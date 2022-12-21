package rummage.RummageMarket.Domain.Interest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InterestRepostiory extends JpaRepository<Interest, Integer> {

    @Modifying
    @Query(value = "INSERT INTO Interest(post_Id, user_Id, create_date) VALUES(:postId, :principalId, now())", nativeQuery = true)
    int mInterest(int postId, int principalId);

    @Modifying
    @Query(value = "DELETE FROM Interest WHERE post_Id = :postId AND user_Id = :principalId", nativeQuery = true)
    int mDisinterest(int postId, int principalId);
}

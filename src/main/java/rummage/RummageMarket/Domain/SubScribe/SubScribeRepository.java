package rummage.RummageMarket.Domain.SubScribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubScribeRepository extends JpaRepository<SubScribe, Integer>{

	@Modifying
	@Query(value = "INSERT INTO subscribe(fromUserId,toUserId,createDate) VALUES(:fromUserId,:toUserId,now())", nativeQuery = true)
	void subscribe(int fromUserId, int toUserId);

	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
	void unsubscribe(int fromUserId, int toUserId);

}

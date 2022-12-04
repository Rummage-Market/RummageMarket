package rummage.RummageMarket.Domain.Neighbor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NeighborRepository extends JpaRepository<Neighbor, Integer> {

    @Modifying
    @Query(value = "INSERT INTO neighbor(fromUserId,toUserId,createDate) VALUES(:fromUserId,:toUserId,now())", nativeQuery = true)
    void neighbor(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "DELETE FROM neighbor WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void unNeighbor(int fromUserId, int toUserId);

    @Query(value = "SELECT COUNT(*) FROM neighbor WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
    int neighborState(int principalId, int pageUserId);

    @Query(value = "SELECT COUNT(*) FROM neighbor WHERE fromUserId = :pageUserId", nativeQuery = true)
    int neighborCount(int pageUserId);

}

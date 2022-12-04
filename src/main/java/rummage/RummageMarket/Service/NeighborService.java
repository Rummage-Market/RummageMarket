package rummage.RummageMarket.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rummage.RummageMarket.Domain.Neighbor.NeighborRepository;
import rummage.RummageMarket.Handler.Ex.CustomApiException;
import rummage.RummageMarket.Web.Dto.Neighbor.NeighborDto;

@Service
public class NeighborService {

    @Autowired
    NeighborRepository neighborRepository;

    @Autowired
    EntityManager em;

    @Transactional
    public void neighbor(int fromUserId, int toUserId) {
        try {
            neighborRepository.neighbor(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독한 상태입니다.");
        }

    }

    @Transactional
    public void unNeighbor(int fromUserId, int toUserId) {
        try {
            neighborRepository.unNeighbor(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독한 상태입니다.");
        }

    }

    @Transactional(readOnly = true)
    public List<NeighborDto> neighborList(int principalId, int pageUserId) {

        // 쿼리준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.nickname, u.profileImage, ");
        sb.append("if ((SELECT 1 FROM neighbor WHERE fromUserId = ? AND toUserId = u.id), 1, 0) neighborState, ");
        sb.append("if ((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN neighbor s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?");// 세미콜론 첨부하면 안됨

        // 쿼리 파라민터 설정
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        // 쿼리실행(qlrm 라이브러리 필요 = Dto에 결과를 매핑하기 위해서)
        JpaResultMapper result = new JpaResultMapper();
        List<NeighborDto> neighborDtos = result.list(query, NeighborDto.class);

        return neighborDtos;
    }
}

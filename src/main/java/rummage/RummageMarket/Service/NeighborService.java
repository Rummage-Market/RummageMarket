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

    // 이웃맺기
    @Transactional
    public void neighbor(int fromUserId, int toUserId) {
        try {
            neighborRepository.neighbor(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독한 상태입니다.");
        }

    }

    // 이웃끊기
    @Transactional
    public void unNeighbor(int fromUserId, int toUserId) {
        try {
            neighborRepository.unNeighbor(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독한 상태입니다.");
        }

    }

    // 프로필 유저가 맺은 이웃들의 리스트 & 로그인된 유저 관점에서의 이웃 유무를 알 수 있는 메서드
    @Transactional(readOnly = true)
    public List<NeighborDto> neighborList(int principalId, int pageUserId) {

        // 쿼리준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.nickname, u.profile_image, ");
        sb.append("if ((SELECT 1 FROM neighbor WHERE from_user_id = ? AND to_user_id = u.id), 1, 0) neighborState, ");
        sb.append("if ((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN neighbor s ");
        sb.append("ON u.id = s.to_user_id ");
        sb.append("WHERE s.from_user_id = ?");

        // 쿼리 파라미터 설정
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        // 쿼리실행
        JpaResultMapper result = new JpaResultMapper();
        List<NeighborDto> neighborDtos = result.list(query, NeighborDto.class);

        return neighborDtos;
    }
}

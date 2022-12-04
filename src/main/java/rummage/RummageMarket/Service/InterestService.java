package rummage.RummageMarket.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.Interest.InterestRepostiory;

@RequiredArgsConstructor
@Service
public class InterestService {

    private final InterestRepostiory interestRepostiory;

    @Transactional
    public void haveInterest(int postId, int principalid) {
        interestRepostiory.mInterest(postId, principalid);
    }

    @Transactional
    public void haveNoInterest(int postId, int principalid) {
        interestRepostiory.mDisinterest(postId, principalid);
    }
}

package rummage.RummageMarket.Service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rummage.RummageMarket.Domain.Interest.InterestRepostiory;

@RequiredArgsConstructor
@Service
public class InterestService {
    private final InterestRepostiory interestRepostiory;
}

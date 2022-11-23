package rummage.RummageMarket.Service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rummage.RummageMarket.Domain.SubScribe.SubScribeRepository;
import rummage.RummageMarket.Handler.Ex.CustomApiException;

@Service
public class SubScribeService {
	
	@Autowired
	SubScribeRepository subScribeRepository;
	
	@Transactional
	public void subscribe(int fromUserId, int toUserId) {
		try {
			subScribeRepository.subscribe(fromUserId,toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독한 상태입니다.");
		}
		
	}

	@Transactional
	public void unsubscribe(int fromUserId, int toUserId) {
		try {
			subScribeRepository.unsubscribe(fromUserId,toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독한 상태입니다.");
		}
		
	}
}

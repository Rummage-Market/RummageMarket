package rummage.RummageMarket.Handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice // 모든 exception을 다 낚아채는 어노테이션
public class ControllerExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public String validationException(RuntimeException e) {
		return e.getMessage();
	}
	
}

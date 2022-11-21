package rummage.RummageMarket.Handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import rummage.RummageMarket.Handler.Ex.CustomValidationException;

@RestController
@ControllerAdvice // 모든 exception을 다 낚아채는 어노테이션
public class ControllerExceptionHandler {

	@ExceptionHandler(CustomValidationException.class)
	public Map<String, String> validationException(CustomValidationException e) {
		return e.getErrorMap();
	}
	
}
